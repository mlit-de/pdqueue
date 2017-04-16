package de.mlit.pdqueue;

/**
 * Created by user on 4/14/17.
 */
interface PDQueueFactory<E> {

    /**
     * Not public api
     * @return
     */
    PDQueue<E> internalEmpty();

    /**
     * Not public api
     * @param e0
     * @return
     */
    PDQueue<E> internalSingleton(E e0);

    /**
     * Not public api
     * @param e0
     * @param e1
     * @return
     */
    PDQueue<E> internalPair(E e0, E e1);

    /**
     * Not public api
     * @param e0
     * @param e1
     * @param e2
     * @return
     */
    PDQueue<E> internalTriple(E e0, E e1, E e2);

    /**
     * Not public api
     * @param e0
     * @param e1
     * @param e2
     * @param e3
     * @return
     */
    PDQueue<E> internalQuadruple(E e0, E e1, E e2, E e3);

    /**
     * Not public api
     * @param left
     * @param middle
     * @param right
     * @return
     */
    PDQueue<E> internalComplQ(PDQueue<E> left, PDQueue<PDQueue<E>> middle, PDQueue<E> right);

    /**
     * Not public api
     * @param left
     * @param right
     * @return
     */
    PDQueue<E> internalComplQ(PDQueue<E> left, PDQueue<E> right);

    /**
     * Not public api
     * @param q1
     * @param q2
     * @return
     */
    default PDQueue<E> internalConcatenate(PDQueue<E> q1, PDQueue<E> q2) {
        int k1 = q1.kind();
        int k2 = q2.kind();
        if (k1 < k2) {
            return q1.appendLTo(q2);
        } else if (k2 < 5) {
            return q2.appendRTo(q1);
        } else {
            PDQueue.DQn<E> dq1 = (PDQueue.DQn<E>) q1;
            PDQueue.DQn<E> dq2 = (PDQueue.DQn<E>) q2;
            return internalComplQ(dq1.left, dq1.packR().appendLTo(dq2.packL()), dq2.right);
        }
    }

    /**
     * Not public api
     * @param e
     * @return
     */
    int internalSizeOf(E e);

    interface Top<A> extends PDQueueFactory<A> {

        static Top INSTANCE = new Top(){};

        static class Q0<A> extends PDQueue.DQ0<A> implements Top<A> {}

        static class Q1<A> extends PDQueue.DQ1<A> implements Top<A> {
            public Q1(int size, A e0) {
                super(size, e0);
            }
        }


        static class Q2<A> extends PDQueue.DQ2<A> implements Top<A> {
            Q2(int size, A e0, A e1) {
                super(size, e0, e1);
            }
        }

        static class Q3<A> extends PDQueue.DQ3<A> implements Top<A> {
            Q3(int size, A e0, A e1, A e2) {
                super(size, e0, e1, e2);
            }
        }

        static class Q4<A> extends PDQueue.DQ4<A> implements Top<A> {
            Q4(int size, A e0, A e1, A e2, A e3) {
                super(size, e0, e1, e2, e3);
            }
        }

        static class Qn<A> extends PDQueue.DQn<A> implements Top<A> {
            Qn(int size, PDQueue<A> left, PDQueue<PDQueue<A>> middle, PDQueue<A> right) {
                super(size, left, middle, right);
            }
        }

        Top.Q0 EMPTY = new Top.Q0();

        @Override
        default public PDQueue<A> internalEmpty() {
            return EMPTY;
        }

        @Override
        default public PDQueue<A> internalSingleton(A e0) {
            return new Top.Q1(1,e0);
        }

        @Override
        default public PDQueue<A> internalPair(A e0, A e1) {
            return new Top.Q2(2, e0, e1);
        }

        @Override
        default public PDQueue<A> internalTriple(A e0, A e1, A e2) {
            return new Top.Q3(3, e0, e1, e2);
        }

        @Override
        default public PDQueue<A> internalQuadruple(A e0, A e1, A e2, A e3) {
            return new Top.Q4(4, e0, e1, e2, e3);
        }

        @Override
        default public PDQueue<A> internalComplQ(PDQueue<A> left, PDQueue<PDQueue<A>> middle, PDQueue<A> right) {
            return new Top.Qn(left.size()+ middle.size()+right.size(), left, middle, right);
        }

        @Override
        default PDQueue<A> internalComplQ(PDQueue<A> left, PDQueue<A> right) {
            return new Top.Qn(left.size()+right.size(), left, Nest.EMPTY, right);
        }

        @Override
        default public int internalSizeOf(A A) {
            return 1;
        }
    }


    interface Nest<E>  extends PDQueueFactory<PDQueue<E>> {


        static class Q0<E> extends PDQueue.DQ0<PDQueue<E>> implements Nest<E> {

        }

        static class Q1<E> extends PDQueue.DQ1<PDQueue<E>> implements Nest<E> {
            Q1(int size, PDQueue<E> e0) {
                super(size, e0);
            }
        }

        static class Q2<E> extends PDQueue.DQ2<PDQueue<E>> implements Nest<E> {
            Q2(int size, PDQueue<E> e0, PDQueue<E> e1) {
                super(size, e0, e1);
            }
        }

        static class Q3<E> extends PDQueue.DQ3<PDQueue<E>> implements Nest<E> {
            Q3(int size, PDQueue<E> e0, PDQueue<E> e1, PDQueue<E> e2) {
                super(size, e0, e1, e2);
            }
        }

        static class Q4<E> extends PDQueue.DQ4<PDQueue<E>> implements Nest<E> {
            Q4(int size, PDQueue<E> e0, PDQueue<E> e1, PDQueue<E> e2, PDQueue<E> e3) {
                super(size, e0, e1, e2, e3);
            }
        }

        static class Qn<E> extends PDQueue.DQn<PDQueue<E>> implements Nest<E> {

            Qn(int size, PDQueue<PDQueue<E>> left, PDQueue<PDQueue<PDQueue<E>>> middle, PDQueue<PDQueue<E>> right) {
                super(size, left, middle, right);
            }
        }


        Q0 EMPTY = new Q0();


        public default PDQueue<PDQueue<E>> internalEmpty() {
            return EMPTY;
        }


        default PDQueue<PDQueue<E>> internalSingleton(PDQueue<E> e0) {
            return new Q1(e0.size(),e0);
        }


        default PDQueue<PDQueue<E>> internalPair(PDQueue<E> e0, PDQueue<E> e1) {
            return new Q2(e0.size()+e1.size(), e0, e1);
        }


        default PDQueue<PDQueue<E>> internalTriple(PDQueue<E> e0, PDQueue<E> e1, PDQueue<E> e2) {
            return new Q3(e0.size()+e1.size()+e2.size(), e0, e1, e2);
        }


        default PDQueue<PDQueue<E>> internalQuadruple(PDQueue<E> e0, PDQueue<E> e1, PDQueue<E> e2, PDQueue<E> e3) {
            return new Q4(e0.size()+e1.size()+e2.size()+e3.size(), e0, e1, e2, e3);
        }


        default PDQueue<PDQueue<E>> internalComplQ(PDQueue<PDQueue<E>> left, PDQueue<PDQueue<PDQueue<E>>> middle, PDQueue<PDQueue<E>> right) {
            return new Qn(left.size()+ middle.size()+right.size(), left, middle, right);
        }

        @Override
        default PDQueue<PDQueue<E>> internalComplQ(PDQueue<PDQueue<E>> left, PDQueue<PDQueue<E>> right) {
            return new Qn(left.size()+right.size(), left, Q0.EMPTY, right);
        }

        public default int internalSizeOf(PDQueue<E> dq) {
            return dq.size();
        }

    }


}
