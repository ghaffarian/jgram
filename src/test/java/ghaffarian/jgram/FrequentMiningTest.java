/** * In The Name of Allah ** */
package ghaffarian.jgram;

import ghaffarian.graphs.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import ghaffarian.jgram.mining.frequent.*;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Testing FSM implementations.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class FrequentMiningTest {

    private final String INPUT_RES = "src/test/resources/";
    private static final String OUTPUT_DIR = "out/";
    
    @BeforeClass
    public static void cleanup() {
        File out = new File(OUTPUT_DIR);
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
        List<Graph<String, String>> graphDataset = GraphReader.readDotDataset(INPUT_RES + "set-1/");
        FrequentSubgraphMining<String, String> fsm = new NaiveFSM<>(0.10f);
        System.out.println("FSM started ...");
        Set<Graph<String, String>> patterns = fsm.mine(graphDataset);
        System.out.println("FSM finished;  # of maximal-patterns = " + patterns.size());
        File patternsDir = new File(OUTPUT_DIR + "patterns-1/");
        patternsDir.mkdirs();
        int cnt = 1;
        for (Graph<String, String> pattern: patterns) {
            GraphWriter.writeDOT(pattern, String.format(OUTPUT_DIR + "patterns-1/pattern_%d.dot", cnt));
            ++cnt;
        }
        assertTrue(patterns.size() >= 4);
        //
        graphDataset = GraphReader.readDotDataset(INPUT_RES + "set-2/");
        fsm = new NaiveFSM<>(0.10f);
        System.out.println("FSM started ...");
        patterns = fsm.mine(graphDataset);
        System.out.println("FSM finished;  # of maximal-patterns = " + patterns.size());
        patternsDir = new File(OUTPUT_DIR + "patterns-2/");
        patternsDir.mkdirs();
        cnt = 1;
        for (Graph<String, String> pattern: patterns) {
            GraphWriter.writeDOT(pattern, String.format(OUTPUT_DIR + "patterns-2/pattern_%d.dot", cnt));
            ++cnt;
        }
        assertTrue(patterns.size() >= 4);
    }

}
