package dip;

import java.io.IOException;
import java.util.Scanner;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

public class Neur {

	@SuppressWarnings("resource")
	public static void main(String... args) throws IOException, InterruptedException {
		double[] image = Img.takePic();
		DataSet training = new DataSet(image.length, 4);
		double[] result;
		MultiLayerPerceptron mlp=new MultiLayerPerceptron(TransferFunctionType.SIGMOID, image.length, ((image.length+4)/2), 4);
		Scanner sc = new Scanner(System.in);
		System.out.println("wanna train?");
		if (sc.next().charAt(0) == 'y') {
			for (int i = 0; i < 50; i++) {
				// Thread.sleep(1500);
				image = Img.takePic();
				char ch = sc.next().charAt(0);
				result = new double[] { 0, 0, 0, 0 };
				if (ch == 'w')
					result = new double[] { 1, 0, 0, 0 };
				else if (ch == 'a')
					result = new double[] { 0, 1, 0, 0 };
				else if (ch == 's')
					result = new double[] { 0, 0, 1, 0 };
				else if (ch == 'd')
					result = new double[] { 0, 0, 0, 1 };
				else
					break;
				training.addRow(new DataSetRow(image, result));
			}
			training.saveAsTxt("data.txt",",");
			mlp.learn(training);
			mlp.save("mlp.nnet");
		} else {
			@SuppressWarnings("rawtypes")
			NeuralNetwork nnet=NeuralNetwork.createFromFile("mlp.nnet");
			for (int i = 0; i >-2; i++) {
				Thread.sleep(1500);
				image = Img.takePic();
				result = new double[] { 0, 0, 0, 0 };
				char ch=sc.next().charAt(0);
				if (ch == 'e')
					return;
				nnet.setInput(image);
				nnet.calculate();
				result=nnet.getOutput();
				double max=result[0];
				int index=0;
				for(int j=1; j<4; j++){
					if(result[j]>max){
						max=result[j];
						index=j;
					}
				}
				if(index==0)
					System.out.println("w");
				else if(index==1)
					System.out.println("a");
				else if(index==2)
					System.out.println("s");
				else if(index==3)
					System.out.println("d");
			}
		}
	}

}
