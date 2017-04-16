package de.mlit.pdqueue;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;

/*
 * Created by user on 4/14/17.
 */

/**
 * This class represents a persistent double ended queue with elements of type E
 * @param <E> the type of the elements in the queue
 */
public abstract class PDQueue<E> implements PDQueueFactory<E> {

    /**
     * Returns an empty queue.
     * @param <E> the type of the elements in the queue
     * @return an emptye PDQueue
     */
    public static <E> PDQueue<E> empty() {
        return Top.EMPTY;
    }

    /**
     * Returns a singleton queue with the given element
     * @param e the element to be contained in the queue
     * @param <E> the type of the elements in the queue
     * @return the queue containing just the one element e
     */
    public static <E> PDQueue<E> singleton(E e) {
        return Top.INSTANCE.internalSingleton(e);
    }

    /**
     * Returns the concatenation of two queues
     * @param q1 the left queue
     * @param q2 the right queue
     * @param <E> the type of the elements in the queue
     * @return the concatenation of q1 and q2
     */

    public static <E> PDQueue<E> concat(PDQueue<E> q1, PDQueue<E> q2) {
        return Top.INSTANCE.internalConcatenate(q1, q2);
    }

    protected final int size;

    /**
     * Gives the size of the queue, which is the number of elements contained in it
     * @return the size
     */

    public int size() {
        return size;
    }

    protected PDQueue(int size) {
        this.size = size;
    }


    /**
     * Returns the n-th element (zero based) of the queue. The 0-th element is the leftmost elmenet, the (size()-1)-th element the right most.
     * @throws IndexOutOfBoundsException if n&lt;0 or n&gt;=size()
     * @param n the index
     * @return the n-th element
     */
    public E get(int n) {
        return get(n, (k, k1) -> {
            if (k1 == 0) {
                return k;
            } else throw new IndexOutOfBoundsException();
        });
    }

    public abstract <A> A get(int n, BiFunction<E, Integer, A> f);

    protected abstract int kind();

    /**
     * Gives the left most element
     * @throws NoSuchElementException if the queue is empty
     * @return the left most element
     */
    public abstract E headL();

    /**
     * Gives the right most element
     * @throws NoSuchElementException if the queue is empty
     * @return the right most element
     */
    public abstract E headR();

    /**
     * Gives a queue with element e added to the left
     * @param e the element to be added
     * @return the queue with element e added to the left
     */
    public abstract PDQueue<E> consL(E e);

    /**
     * Gives a queue with element e added to the right
     * @param e the element to be added
     * @return the queue with element e added to the right
     */
    public abstract PDQueue<E> consR(E e);

    /**
     * Gives a queue with element ee and e added to the left. This result will be the same as the result when calling
     * .consL(e).consL(ee), but is more efficient.
     * @param e the element to be added
     * @return the queue with elements ee and e added to the left
     */
    public abstract PDQueue<E> consConsL(E ee, E e);

    /**
     * Gives a queue with element e and ee added to the right. This result will be the same as the result when calling
     * .consR(e).consR(ee), but is more efficient.
     * @param e the element to be added
     * @return the queue with elements e and ee added to the right
     */
    public abstract PDQueue<E> consConsR(E e, E ee);

    /**
     * Gives the queue with the leftmost element removed.
     * @throws NoSuchElementException if the queue is empty
     * @return the queue with the leftmost element removed
     */
    public abstract PDQueue<E> tailL();

    /**
     * Gives the queue with the rightmost element removed.
     * @throws NoSuchElementException if the queue is empty
     * @return the queue with the rightmost element removed
     */
    public abstract PDQueue<E> tailR();

    /**
     * Appends this queue from left to the queue q. q1.appendLTo(q2) gives the same result as concat(q1,q2).
     * @param q the queue to append this queue to
     * @return the concatenation of the two queues
     */
    public abstract PDQueue<E> appendLTo(PDQueue<E> q);

    /**
     * Appends this queue from right to the queue q. q1.appendRTo(q2) gives the same result as concat(q2,q1).
     * @param q the queue to append this queue to
     * @return the concatenation of the two queues
     */
    public abstract PDQueue<E> appendRTo(PDQueue<E> q);

    /**
     * Gives a queue with the leftmost element replaced by e
     * @param e the value to replace the leftmost element with
     * @throws NoSuchElementException if the queue is empty
     * @return the queue with the leftmost element replaced by e
     */
    public abstract PDQueue<E> replaceL(E e);

    /**
     * Gives a queue with the rightmost element replaced by e
     * @param e the value to replace the rightmost element with
     * @throws NoSuchElementException if the queue is empty
     * @return the queue with the rightmost element replaced by e
     */
    public abstract PDQueue<E> replaceR(E e);

    /**
     * Returns wether the queue is empty. q.isEmpty() returns the same result than q.size() == 0
     * @return true, if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return false;
    }


    protected PDQueue<E> prepareConsL() {
        return this;
    }

    protected PDQueue<E> prepareConsR() {
        return this;
    }

    protected PDQueue<E> prepareTailL() {
        return this;
    }

    protected PDQueue<E> prepareTailR() {
        return this;
    }

    abstract static class DQ0<E> extends PDQueue<E> implements PDQueueFactory<E> {

        protected DQ0() {
            super(0);
        }

        protected int kind() {
            return 0;
        }

        public E headL() {
            throw new NoSuchElementException();
        }

        public E headR() {
            throw new NoSuchElementException();
        }

        public PDQueue<E> consL(E e) {
            return internalSingleton(e);
        }

        public PDQueue<E> consR(E e) {
            return internalSingleton(e);
        }


        @Override
        public PDQueue<E> consConsL(E ee, E e) {
            return internalPair(ee, e);
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            return internalPair(e, ee);
        }

        public PDQueue<E> tailL() {
            throw new NoSuchElementException();
        }

        public PDQueue<E> tailR() {
            throw new NoSuchElementException();
        }


        public PDQueue<E> appendLTo(PDQueue<E> q) {
            return q;
        }

        public PDQueue<E> appendRTo(PDQueue<E> q) {
            return q;
        }

        @Override
        public PDQueue<E> replaceL(E e) {
            throw new NoSuchElementException();
        }

        @Override
        public PDQueue<E> replaceR(E e) {
            throw new NoSuchElementException();
        }

        public boolean isEmpty() {
            return true;
        }

        @Override
        public <A> A get(int n, BiFunction<E, Integer, A> f) {
            throw new IndexOutOfBoundsException();
        }


    }

    abstract static class DQ1<E> extends PDQueue<E> implements PDQueueFactory<E> {
        private final E e0;

        DQ1(int size, E e0) {
            super(size);
            this.e0 = e0;
        }

        protected int kind() {
            return 1;
        }

        public E headL() {
            return e0;
        }

        public E headR() {
            return e0;
        }

        public PDQueue<E> consL(E e) {
            return internalPair(e, e0);
        }

        public PDQueue<E> consR(E e) {
            return internalPair(e0, e);
        }

        @Override
        public PDQueue<E> consConsL(E ee, E e) {
            return internalTriple(ee, e, e0);
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            return internalTriple(e0, e, ee);
        }

        public PDQueue<E> tailL() {
            return internalEmpty();
        }

        public PDQueue<E> tailR() {
            return internalEmpty();
        }


        public PDQueue<E> appendLTo(PDQueue<E> q) {
            return q.consL(e0);
        }


        public PDQueue<E> appendRTo(PDQueue<E> q) {
            return q.consR(e0);
        }

        @Override
        public PDQueue<E> replaceL(E e) {
            return internalSingleton(e);
        }

        @Override
        public PDQueue<E> replaceR(E e) {
            return internalSingleton(e);
        }

        @Override
        public <A> A get(int n, BiFunction<E, Integer, A> f) {
            return f.apply(e0, n);
        }

    }

    abstract static class DQ2<E> extends PDQueue<E> implements PDQueueFactory<E> {
        private final E e0, e1;

        DQ2(int size, E e0, E e1) {
            super(size);
            this.e0 = e0;
            this.e1 = e1;
        }

        protected int kind() {
            return 2;
        }

        public E headL() {
            return e0;
        }

        public E headR() {
            return e1;
        }

        public PDQueue<E> consL(E e) {
            return internalTriple(e, e0, e1);
        }

        public PDQueue<E> consR(E e) {
            return internalTriple(e0, e1, e);
        }

        @Override
        public PDQueue<E> consConsL(E ee, E e) {
            return internalQuadruple(ee, e, e0, e1);
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            return internalQuadruple(e0, e1, e, ee);
        }

        public PDQueue<E> tailL() {
            return internalSingleton(e1);
        }

        public PDQueue<E> tailR() {
            return internalSingleton(e0);
        }

        @Override
        public PDQueue<E> appendLTo(PDQueue<E> q) {
            return q.consL(e1).consL(e0);
        }

        @Override
        public PDQueue<E> appendRTo(PDQueue<E> q) {
            return q.consR(e0).consR(e1);
        }

        @Override
        public PDQueue<E> replaceL(E e) {
            return internalPair(e, e1);
        }

        @Override
        public PDQueue<E> replaceR(E e) {
            return internalPair(e0, e);
        }

        @Override
        public <A> A get(int n, BiFunction<E, Integer, A> f) {
            int m = internalSizeOf(e0);
            return n < m ? f.apply(e0, n) : f.apply(e1, n - m);
        }

    }

    abstract static class DQ3<E> extends PDQueue<E> implements PDQueueFactory<E> {
        private final E e0, e1, e2;

        DQ3(int size, E e0, E e1, E e2) {
            super(size);
            this.e0 = e0;
            this.e1 = e1;
            this.e2 = e2;
        }

        protected int kind() {
            return 3;
        }

        public E headL() {
            return e0;
        }

        public E headR() {
            return e2;
        }

        public PDQueue<E> consL(E e) {
            return internalQuadruple(e, e0, e1, e2);
        }

        public PDQueue<E> consR(E e) {
            return internalQuadruple(e0, e1, e2, e);
        }

        public PDQueue<E> tailL() {
            return internalPair(e1, e2);
        }

        public PDQueue<E> tailR() {
            return internalPair(e0, e1);
        }

        @Override
        public PDQueue<E> appendLTo(PDQueue<E> q) {
            return q.consL(e2).consL(e1).consL(e0);
        }

        @Override
        public PDQueue<E> appendRTo(PDQueue<E> q) {
            return q.consR(e0).consR(e1).consR(e2);
        }

        @Override
        public PDQueue<E> consConsL(E ee, E e) {
            return internalComplQ(internalPair(ee, e), this);
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            return internalComplQ(this, internalPair(e, ee));
        }

        @Override
        public PDQueue<E> replaceL(E e) {
            return internalTriple(e, e1, e2);
        }

        @Override
        public PDQueue<E> replaceR(E e) {
            return internalTriple(e0, e1, e);
        }

        @Override
        public <A> A get(int n, BiFunction<E, Integer, A> f) {
            int m0 = internalSizeOf(e0);
            if (n < m0) {
                return f.apply(e0, n);
            }
            int m1 = internalSizeOf(e1) + m0;
            if (n < m1) {
                return f.apply(e1, n - m0);
            }
            return f.apply(e2, n - m1);
        }

    }

    abstract static class DQ4<E> extends PDQueue<E> implements PDQueueFactory<E> {
        private final E e0, e1, e2, e3;

        DQ4(int size, E e0, E e1, E e2, E e3) {
            super(size);
            this.e0 = e0;
            this.e1 = e1;
            this.e2 = e2;
            this.e3 = e3;
        }

        protected int kind() {
            return 4;
        }

        public E headL() {
            return e0;
        }

        public E headR() {
            return e3;
        }

        public PDQueue<E> consL(E e) {
            return internalComplQ(internalPair(e, e0), internalTriple(e1, e2, e3));
        }

        public PDQueue<E> consR(E e) {
            return internalComplQ(internalTriple(e0, e1, e2), internalPair(e3, e));
        }

        public PDQueue<E> tailL() {
            return internalTriple(e1, e2, e3);
        }

        public PDQueue<E> tailR() {
            return internalTriple(e0, e1, e2);
        }

        @Override
        public PDQueue<E> appendLTo(PDQueue<E> q) {
            return q.consL(e3).consL(e2).consL(e1).consL(e0);
        }

        @Override
        public PDQueue<E> appendRTo(PDQueue<E> q) {
            return q.consR(e0).consR(e1).consR(e2).consR(e3);
        }

        @Override
        public PDQueue<E> consConsL(E ee, E e) {
            return internalComplQ(internalPair(ee, e), this);
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            return internalComplQ(this, internalPair(e, ee));
        }

        @Override
        public PDQueue<E> replaceL(E e) {
            return internalQuadruple(e, e1, e2, e3);
        }

        @Override
        public PDQueue<E> replaceR(E e) {
            return internalQuadruple(e0, e1, e2, e);
        }

        @Override
        public <A> A get(int n, BiFunction<E, Integer, A> f) {
            int m0 = internalSizeOf(e0);
            int m1 = internalSizeOf(e1) + m0;

            if (n < m1) {
                return n < m0 ? f.apply(e0, n) : f.apply(e1, n - m0);
            } else {
                int m2 = internalSizeOf(e2) + m1;
                return n < m2 ? f.apply(e2, n - m1) : f.apply(e3, n - m2);
            }
        }

        PDQueue<E> leftPair() {
            return internalPair(e0, e1);
        }

        PDQueue<E> rightPair() {
            return internalPair(e2, e3);
        }


    }

    abstract static class DQn<E> extends PDQueue<E> {


        final PDQueue<E> left;
        final PDQueue<PDQueue<E>> middle;
        final PDQueue<E> right;

        DQn(int size, PDQueue<E> left, PDQueue<PDQueue<E>> middle, PDQueue<E> right) {
            super(size);
            this.left = left;
            this.middle = middle;
            this.right = right;
        }


        protected int kind() {
            return 5;
        }

        public E headL() {
            return left.headL();
        }

        public E headR() {
            return right.headR();
        }

        public PDQueue<E> consL(E e) {
            switch (left.kind()) {
                case 2:
                    return internalComplQ(left.consL(e), middle, right);
                case 3:
                    return internalComplQ(left.consL(e), middle.prepareConsL(), right);
                case 4:
                    return internalComplQ(internalPair(e, left.headL()), middle.consL(left.tailL()), right);
                default:
                    throw new IllegalStateException("not reachable");
            }
        }

        public PDQueue<E> consR(E e) {
            switch (right.kind()) {
                case 2:
                    return internalComplQ(left, middle, right.consR(e));
                case 3:
                    return internalComplQ(left, middle.prepareConsR(), right.consR(e));
                case 4:
                    return internalComplQ(left, middle.consR(right.tailR()), internalPair(right.headR(), e));
                default:
                    throw new IllegalStateException("not reachable");
            }

        }

        @Override
        public PDQueue<E> consConsL(E ee, E e) {
            switch (left.kind()) {
                case 2:
                    return internalComplQ(left.consConsL(ee, e), middle.prepareConsL(), right);
                case 3:
                    return internalComplQ(internalPair(ee, e), middle.consL(left), right);
                case 4:
                    return internalComplQ(internalTriple(ee, e, left.headL()), middle.consL(left.tailL()).prepareConsL(), right);
                default:
                    throw new IllegalStateException("not reachable");

            }
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            switch (right.kind()) {
                case 2:
                    return internalComplQ(left, middle.prepareConsR(), right.consConsR(e, ee));
                case 3:
                    return internalComplQ(left, middle.consR(right), internalPair(e, ee));
                case 4:
                    return internalComplQ(left, middle.consR(right.tailR()), internalTriple(right.headR(), e, ee));
                default:
                    throw new IllegalStateException("not reachable");
            }
        }

        public PDQueue<E> tailL() {
            switch (left.kind()) {
                case 2: {
                    if (middle.isEmpty()) {
                        return right.consL(left.headR());
                    } else {
                        return internalComplQ(middle.headL().consL(left.headR()), middle.tailL(), right);
                    }
                }
                case 3: {
                    return internalComplQ(left.tailL(), middle.prepareTailL(), right);
                }
                case 4: {
                    return internalComplQ(left.tailL(), middle, right);
                }
                default:
                    throw new IllegalStateException("not reachable");
            }
        }

        public PDQueue<E> tailR() {
            switch (right.kind()) {
                case 2: {
                    if (middle.isEmpty()) {
                        return left.consR(right.headL());
                    } else {
                        return internalComplQ(left, middle.tailR(), middle.headR().consR(right.headL()));
                    }
                }
                case 3: {
                    return internalComplQ(left, middle.prepareTailR(), right.tailR());
                }
                case 4: {
                    return internalComplQ(left, middle, right.tailR());
                }
                default:
                    throw new IllegalStateException("not reachable");
            }
        }

        @Override
        public PDQueue<E> appendLTo(PDQueue<E> q) {
            return internalConcatenate(this, q);
        }

        @Override
        public PDQueue<E> appendRTo(PDQueue<E> q) {
            return internalConcatenate(q, this);
        }

        PDQueue<PDQueue<E>> packR() {
            switch (right.kind()) {
                case 2:
                case 3:
                    return middle.consR(right);
                case 4:
                    DQ4<E> r = (DQ4<E>) right;
                    return middle.consR(r.leftPair()).consR(r.rightPair());
                default:
                    throw new IllegalStateException();
            }
        }

        PDQueue<PDQueue<E>> packL() {
            switch (left.kind()) {
                case 2:
                case 3:
                    return middle.consL(left);
                case 4:
                    DQ4<E> l = (DQ4<E>) left;
                    return middle.consL(l.rightPair()).consL(l.leftPair());
                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        protected PDQueue<E> prepareConsL() {
            if (left.kind() == 4) {
                DQ4<E> l = (DQ4<E>) left;
                return internalComplQ(l.leftPair(), middle.consL(l.rightPair()), right);
            } else {
                return this;
            }
        }

        @Override
        protected PDQueue<E> prepareConsR() {
            if (right.kind() == 4) {
                DQ4<E> r = (DQ4<E>) right;
                return internalComplQ(left, middle.consR(r.leftPair()), ((DQ4<E>) right).rightPair());
            } else {
                return this;
            }
        }

        @Override
        protected PDQueue<E> prepareTailL() {
            if (left.kind() == 2 && !middle.isEmpty()) {
                PDQueue<E> l = middle.headL();
                if (l.kind() == 2) {
                    return internalComplQ(left.consConsR(l.headL(), l.headR()), middle.tailL(), right);
                } else {
                    return internalComplQ(left.consR(l.headL()), middle.replaceL(l.tailL()), right);
                }
            } else {
                return this;
            }
        }

        @Override
        protected PDQueue<E> prepareTailR() {
            if (right.kind() == 2 && !middle.isEmpty()) {
                PDQueue<E> r = middle.headR();
                if (r.kind() == 2) {
                    return internalComplQ(left, middle.tailR(), right.consConsL(r.headL(), r.headR()));
                } else {
                    return internalComplQ(left, middle.replaceR(r.tailR()), right.consL(r.headR()));
                }
            } else {
                return this;
            }
        }

        @Override
        public PDQueue<E> replaceL(E e) {
            return internalComplQ(left.replaceL(e), middle, right);
        }

        @Override
        public PDQueue<E> replaceR(E e) {
            return internalComplQ(left, middle, right.replaceR(e));
        }

        @Override
        public <A> A get(int n, BiFunction<E, Integer, A> f) {
            int m0 = left.size();
            if (n < m0) {
                return left.get(n, f);
            }
            int m1 = middle.size() + m0;
            if (n < m1) {
                return middle.get(n - m0, (q, n0) -> q.get(n0, f));
            } else {
                return right.get(n - m1, f);
            }
        }

    }
}
