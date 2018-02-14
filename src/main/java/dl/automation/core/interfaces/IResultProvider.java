/**
 * 
 */
package dl.automation.core.interfaces;

import rx.Observable;



public interface IResultProvider<T> {
	public Observable<T> getResults();
	public void publishResult(T value);
}
