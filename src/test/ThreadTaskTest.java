package test;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadTaskTest{
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newCachedThreadPool();
		ArrayList<Future<Integer>> resultList = new ArrayList<>();
		
		//创建并提交任务1
		AddNumberTask task1 = new AddNumberTask(1, 5000);
		Future<Integer> future1 = executor.submit(task1);
		resultList.add(future1);
		
		//创建并提交任务2
		AddNumberTask task2 = new AddNumberTask(5001, 10000);
		Future<Integer> future2 = executor.submit(task2);
		resultList.add(future2);
		
		executor.shutdown();
		
		int total = 0;
		
		for(Future<Integer> future : resultList){
			while(true){
				if(future.isDone() && !future.isCancelled()){
					int sum = future.get();
					total += sum;
					break;
				}
				else{
					Thread.sleep(100);
				}
			}
		}
		
		System.out.println("total sum is " + total);
	}

}

class AddNumberTask implements Callable<Integer>{
	private int start;
	private int end;
	
	public AddNumberTask(int start, int end) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.end = end;
	}
	
	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		int totalSum = 0;
		
		for(int i = start; i <= end; i++){
			totalSum += i;
		}
		
		Thread.sleep(5000);
		
		return totalSum;
	}
	
}
