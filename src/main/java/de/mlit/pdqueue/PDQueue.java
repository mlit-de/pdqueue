package de.mlit.pdqueue;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;

/**
 * Created by user on 4/14/17.
 */
public abstract class PDQueue<E> implements PDQueueFactory<E> {

    public static <A> PDQueue<A> empty() {
        return Top.EMPTY;
    }

    public static <A> PDQueue<A> singleton(A a) {
        return Top.INSTANCE.createSingleton(a);
    }

    public static <A> PDQueue<A> concat(PDQueue<A> q1, PDQueue<A> q2) {
        return Top.INSTANCE.createConcatenation(q1, q2);
    }

    protected final int size;

    public int size() {
        return size;
    }

    protected PDQueue(int size) {
        this.size = size;
    }


    public E get(int n) {
        return get(n, (k, k1) -> {
            if (k1 == 0) {
                return k;
            } else throw new IndexOutOfBoundsException();
        });
    }

    public abstract <A> A get(int n, BiFunction<E, Integer, A> f);

    protected abstract int kind();

    public abstract E headL();

    public abstract E headR();

    public abstract PDQueue<E> consL(E e);

    public abstract PDQueue<E> consR(E e);

    public abstract PDQueue<E> consConsL(E ee, E e);

    public abstract PDQueue<E> consConsR(E e, E ee);

    public abstract PDQueue<E> tailL();

    public abstract PDQueue<E> tailR();

    public abstract PDQueue<E> appendLTo(PDQueue<E> q);

    public abstract PDQueue<E> appendRTo(PDQueue<E> q);

    public abstract PDQueue<E> changeL(E e);

    public abstract PDQueue<E> changeR(E e);

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
            return createSingleton(e);
        }

        public PDQueue<E> consR(E e) {
            return createSingleton(e);
        }


        @Override
        public PDQueue<E> consConsL(E ee, E e) {
            return createPair(ee, e);
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            return createPair(e, ee);
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
        public PDQueue<E> changeL(E e) {
            throw new NoSuchElementException();
        }

        @Override
        public PDQueue<E> changeR(E e) {
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
            return createPair(e, e0);
        }

        public PDQueue<E> consR(E e) {
            return createPair(e0, e);
        }

        @Override
        public PDQueue<E> consConsL(E ee, E e) {
            return createTriple(ee, e, e0);
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            return createTriple(e0, e, ee);
        }

        public PDQueue<E> tailL() {
            return createEmpty();
        }

        public PDQueue<E> tailR() {
            return createEmpty();
        }


        public PDQueue<E> appendLTo(PDQueue<E> q) {
            return q.consL(e0);
        }


        public PDQueue<E> appendRTo(PDQueue<E> q) {
            return q.consR(e0);
        }

        @Override
        public PDQueue<E> changeL(E e) {
            return createSingleton(e);
        }

        @Override
        public PDQueue<E> changeR(E e) {
            return createSingleton(e);
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
            return createTriple(e, e0, e1);
        }

        public PDQueue<E> consR(E e) {
            return createTriple(e0, e1, e);
        }

        @Override
        public PDQueue<E> consConsL(E ee, E e) {
            return createQuadruple(ee, e, e0, e1);
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            return createQuadruple(e0, e1, e, ee);
        }

        public PDQueue<E> tailL() {
            return createSingleton(e1);
        }

        public PDQueue<E> tailR() {
            return createSingleton(e0);
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
        public PDQueue<E> changeL(E e) {
            return createPair(e, e1);
        }

        @Override
        public PDQueue<E> changeR(E e) {
            return createPair(e0, e);
        }

        @Override
        public <A> A get(int n, BiFunction<E, Integer, A> f) {
            int m = sizeOf(e0);
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
            return createQuadruple(e, e0, e1, e2);
        }

        public PDQueue<E> consR(E e) {
            return createQuadruple(e0, e1, e2, e);
        }

        public PDQueue<E> tailL() {
            return createPair(e1, e2);
        }

        public PDQueue<E> tailR() {
            return createPair(e0, e1);
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
            return createComplQ(createPair(ee, e), this);
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            return createComplQ(this, createPair(e, ee));
        }

        @Override
        public PDQueue<E> changeL(E e) {
            return createTriple(e, e1, e2);
        }

        @Override
        public PDQueue<E> changeR(E e) {
            return createTriple(e0, e1, e);
        }

        @Override
        public <A> A get(int n, BiFunction<E, Integer, A> f) {
            int m0 = sizeOf(e0);
            if (n < m0) {
                return f.apply(e0, n);
            }
            int m1 = sizeOf(e1) + m0;
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
            return createComplQ(createPair(e, e0), createTriple(e1, e2, e3));
        }

        public PDQueue<E> consR(E e) {
            return createComplQ(createTriple(e0, e1, e2), createPair(e3, e));
        }

        public PDQueue<E> tailL() {
            return createTriple(e1, e2, e3);
        }

        public PDQueue<E> tailR() {
            return createTriple(e0, e1, e2);
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
            return createComplQ(createPair(ee, e), this);
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            return createComplQ(this, createPair(e, ee));
        }

        @Override
        public PDQueue<E> changeL(E e) {
            return createQuadruple(e, e1, e2, e3);
        }

        @Override
        public PDQueue<E> changeR(E e) {
            return createQuadruple(e0, e1, e2, e);
        }

        @Override
        public <A> A get(int n, BiFunction<E, Integer, A> f) {
            int m0 = sizeOf(e0);
            int m1 = sizeOf(e1) + m0;

            if (n < m1) {
                return n < m0 ? f.apply(e0, n) : f.apply(e1, n - m0);
            } else {
                int m2 = sizeOf(e2) + m1;
                return n < m2 ? f.apply(e2, n - m1) : f.apply(e3, n - m2);
            }
        }

        PDQueue<E> leftPair() {
            return createPair(e0, e1);
        }

        PDQueue<E> rightPair() {
            return createPair(e2, e3);
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
                    return createComplQ(left.consL(e), middle, right);
                case 3:
                    return createComplQ(left.consL(e), middle.prepareConsL(), right);
                case 4:
                    return createComplQ(createPair(e, left.headL()), middle.consL(left.tailL()), right);
                default:
                    throw new IllegalStateException("not reachable");
            }
        }

        public PDQueue<E> consR(E e) {
            switch (right.kind()) {
                case 2:
                    return createComplQ(left, middle, right.consR(e));
                case 3:
                    return createComplQ(left, middle.prepareConsR(), right.consR(e));
                case 4:
                    return createComplQ(left, middle.consR(right.tailR()), createPair(right.headR(), e));
                default:
                    throw new IllegalStateException("not reachable");
            }

        }

        @Override
        public PDQueue<E> consConsL(E ee, E e) {
            switch (left.kind()) {
                case 2:
                    return createComplQ(left.consConsL(ee, e), middle.prepareConsL(), right);
                case 3:
                    return createComplQ(createPair(ee, e), middle.consL(left), right);
                case 4:
                    return createComplQ(createTriple(ee, e, left.headL()), middle.consL(left.tailL()).prepareConsL(), right);
                default:
                    throw new IllegalStateException("not reachable");

            }
        }

        @Override
        public PDQueue<E> consConsR(E e, E ee) {
            switch (right.kind()) {
                case 2:
                    return createComplQ(left, middle.prepareConsR(), right.consConsR(e, ee));
                case 3:
                    return createComplQ(left, middle.consR(right), createPair(e, ee));
                case 4:
                    return createComplQ(left, middle.consR(right.tailR()), createTriple(right.headR(), e, ee));
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
                        return createComplQ(middle.headL().consL(left.headR()), middle.tailL(), right);
                    }
                }
                case 3: {
                    return createComplQ(left.tailL(), middle.prepareTailL(), right);
                }
                case 4: {
                    return createComplQ(left.tailL(), middle, right);
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
                        return createComplQ(left, middle.tailR(), middle.headR().consR(right.headL()));
                    }
                }
                case 3: {
                    return createComplQ(left, middle.prepareTailR(), right.tailR());
                }
                case 4: {
                    return createComplQ(left, middle, right.tailR());
                }
                default:
                    throw new IllegalStateException("not reachable");
            }
        }

        @Override
        public PDQueue<E> appendLTo(PDQueue<E> q) {
            return createConcatenation(this, q);
        }

        @Override
        public PDQueue<E> appendRTo(PDQueue<E> q) {
            return createConcatenation(q, this);
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
                return createComplQ(l.leftPair(), middle.consL(l.rightPair()), right);
            } else {
                return this;
            }
        }

        @Override
        protected PDQueue<E> prepareConsR() {
            if (right.kind() == 4) {
                DQ4<E> r = (DQ4<E>) right;
                return createComplQ(left, middle.consR(r.leftPair()), ((DQ4<E>) right).rightPair());
            } else {
                return this;
            }
        }

        @Override
        protected PDQueue<E> prepareTailL() {
            if (left.kind() == 2 && !middle.isEmpty()) {
                PDQueue<E> l = middle.headL();
                if (l.kind() == 2) {
                    return createComplQ(left.consConsR(l.headL(), l.headR()), middle.tailL(), right);
                } else {
                    return createComplQ(left.consR(l.headL()), middle.changeL(l.tailL()), right);
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
                    return createComplQ(left, middle.tailR(), right.consConsL(r.headL(), r.headR()));
                } else {
                    return createComplQ(left, middle.changeR(r.tailR()), right.consL(r.headR()));
                }
            } else {
                return this;
            }
        }

        @Override
        public PDQueue<E> changeL(E e) {
            return createComplQ(left.changeL(e), middle, right);
        }

        @Override
        public PDQueue<E> changeR(E e) {
            return createComplQ(left, middle, right.changeR(e));
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
