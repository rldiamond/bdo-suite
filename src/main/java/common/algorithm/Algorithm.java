package common.algorithm;

/**
 * Interface defining an algorithm.
 *
 * @param <T>
 */
public interface Algorithm<T> {

    /**
     * The logic to execute when the algorithm is ran.
     *
     * @return The object to return.
     * @throws AlgorithmException If an error occurs during the run of the algorithm.
     */
    public T run() throws AlgorithmException;

}
