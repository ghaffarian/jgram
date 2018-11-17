/** * In The Name of Allah ** */
package jgram;

import java.io.File;
import java.io.IOException;
import java.util.List;
import jgram.graphs.Graph;
import jgram.io.GraphReader;
import jgram.io.GraphWriter;
import jgram.mining.frequent.FrequentSubgraphMining;
import jgram.mining.frequent.NaiveFSM;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Testing FSM implementations.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class FrequentMiningTest {

    @BeforeClass
    public static void cleanup() {
        File out = new File("graph-data/out/");
        for (File file: out.listFiles()) {
            if (file.isFile())
                file.delete();
            else
                deleteDir(file);
        }
    }
    
    private static void deleteDir(File dir) {
        if (dir.list().length > 0) {
            for (File file: dir.listFiles()) {
                if (file.isFile())
                    file.delete();
                else
                    deleteDir(file);
            }
        }
        dir.delete();
    }

    @Test
    public void naiveFSMTest() throws IOException {
        List<Graph<String, String>> graphDataset = GraphReader.readDotDataset("graph-data/set-1/");
        FrequentSubgraphMining<String, String> fsm = new NaiveFSM<>(0.10f);
        System.out.println("FSM started ...");
        List<Graph<String, String>> patterns = fsm.mine(graphDataset);
        System.out.println("FSM finished;  # of patterns = " + patterns.size());
        File patternsDir = new File("graph-data/out/patterns-1/");
        patternsDir.mkdirs();
        for (int cnt = 0; cnt < patterns.size(); ++cnt)
            GraphWriter.writeDOT(patterns.get(cnt), String.format("graph-data/out/patterns-1/pattern_%d.dot", cnt + 1));
        assertTrue(patterns.size() >= 4);
    }

}
