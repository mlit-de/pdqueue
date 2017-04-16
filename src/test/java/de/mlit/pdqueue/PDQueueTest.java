package de.mlit.pdqueue;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by user on 4/14/17.
 */
public class PDQueueTest {



    public void runTest(Iterator<Integer> script) {


        LinkedList<Integer> vgl = new LinkedList<Integer>();
        PDQueue<Integer> dq = PDQueue.empty();
        LinkedList<Integer> vgl2 = new LinkedList<Integer>();
        PDQueue<Integer> dq2 = PDQueue.<Integer>empty();
        PDQueue<Integer> dq0;
        List<Integer> trace = new ArrayList<>();
        try {
            int left=0;
            int right=1;
            while(script.hasNext()) {
                int r = script.next();
                trace.add(r);
                dq0 = dq;
                switch (r) {
                    case 0:
                        vgl.addFirst(left);
                        dq = dq.consL(left);
                        left+=2;
                        break;
                    case 1:
                        vgl.addLast(right);
                        dq = dq.consR(right);
                        right+=2;
                        break;
                    case 2:
                        if (dq.isEmpty()) {
                            Assert.assertTrue(vgl.isEmpty());
                        } else {
                            Assert.assertEquals(vgl.pollFirst(), dq.headL());
                            dq = dq.tailL();
                        }
                        break;
                    case 3:
                        if (dq.isEmpty()) {
                            Assert.assertTrue(vgl.isEmpty());
                        } else {
                            Assert.assertEquals(vgl.pollLast(), dq.headR());
                            dq = dq.tailR();
                        }
                        break;
                    case 4:
                        PDQueue<Integer> dq1 = dq;
                        dq=dq2; dq2=dq1;
                        LinkedList<Integer> vgl1 = vgl;
                        vgl=vgl2; vgl2=vgl1;
                        break;
                    case 5:
                        dq = PDQueue.concat(dq,dq2);
                        dq2 = PDQueue.empty();
                        vgl.addAll(vgl2);
                        vgl2 = new LinkedList<>();
                        break;
                    case 6:
                        vgl.addFirst(left);
                        vgl.addFirst(left+2);
                        dq = dq.consConsL(left+2,left);
                        left+=4;
                        break;
                    case 7:
                        vgl.addLast(right);
                        vgl.addLast(right+2);
                        dq = dq.consConsR(right, right+2);
                        right+=4;
                        break;
                }
                try {
                    assertQueue(vgl, dq);
                } catch (Error e) {
                     System.out.println(dq0 + "  | " + dq2);
                     System.out.println(r+":"+left+":"+right);
                     System.out.println(dq);
                     throw e;
                }
            }

        } catch (Error ex) {

            System.out.println(trace);
            throw ex;
        }
        while(!vgl.isEmpty()) {
            Assert.assertEquals(vgl.pollFirst(), dq.headL());
            dq=dq.tailL();
        }
        Assert.assertTrue(dq.isEmpty());
    }

    public void assertQueue(LinkedList<Integer> vgl, PDQueue<Integer> dq) {
        Assert.assertEquals(vgl.size(), dq.size());
        for(int n : vgl) {
            int n1 = dq.headL();
            Assert.assertEquals(n, n1);
            dq=dq.tailL();
        }

        Assert.assertTrue(dq.isEmpty());
    }

    @Test
    public void testSingle() throws Exception {
        Iterator<Integer> iterator = new Iterator<Integer>() {
            int ctr = 10000;
            Random random = new Random();

            @Override
            public boolean hasNext() {
                return ctr-- > 0;
            }

            @Override
            public Integer next() {
                return random.nextInt(8);
            }
        };
        runTest(iterator);
    }

    @Test
    public void testBig() {
        PDQueue<Integer> dq= PDQueue.empty();
        for(int i=0; i<10000; i++) {
            dq = dq.consR(i);
            dq = dq.tailR().consR(dq.headR());
        }
        for(int i=0; i<10000; i++) {
            Assert.assertEquals(i, (int)dq.headL());
            dq = dq.tailL();
        }
        PDQueue<Integer> dq2= PDQueue.empty();
        for(int i=0; i<10000; i++) {
            dq = dq.consL(i);
        }
        for(int i=0; i<10000; i++) {
            Assert.assertEquals(i, (int)dq.headR());
            dq = dq.tailR();
        }
    }

    @Test
    public void testAt() {
        PDQueue<Integer> dq= PDQueue.empty();
        for(int i=0; i<10000; i++) {
            dq = dq.consR(i);
            dq = dq.tailR().consR(dq.headR());
        }
        for(int i=0; i<1000; i++) {
            Assert.assertEquals(i, (int)dq.get(i));
        }
    }

}
