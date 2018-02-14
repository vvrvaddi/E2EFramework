/**
 * 
 */
package dl.automation.core.providers;

import dl.automation.core.interfaces.IResultProvider;
import dl.automation.core.utils.ExecutionEventsEmitter;
import rx.Observable;
import rx.subjects.PublishSubject;

public class ResultsProvider<T> implements IResultProvider<T> {
	
	/**
	 * call on result published
	 */
	private final PublishSubject<T> onResultPublished;
	private ExecutionEventsEmitter eventEmitter;
	/**
	 *ResultsProvider constructor
	 */
	public ResultsProvider(ExecutionEventsEmitter eventEmitter) {
		onResultPublished = PublishSubject.create();
		this.eventEmitter = eventEmitter;
		this.eventEmitter.getPulishResult().subscribe(item -> publishResult((T)item));
	}
	

	/* (non-Javadoc)
	 * @see dl.automation.core.providers.IResultProvider#getResults()
	 */
	@Override
	public Observable<T> getResults() {
		return onResultPublished;
	}
	
	/**
	 * @param value
	 */
	public void publishResult(T value){
		onResultPublished.onNext(value);
	}

}
