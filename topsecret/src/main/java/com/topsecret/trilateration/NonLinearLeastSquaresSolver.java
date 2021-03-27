package com.topsecret.trilateration;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresFactory;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer.Optimum;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DiagonalMatrix;

import java.awt.geom.Point2D;

public class NonLinearLeastSquaresSolver {

	protected final TrilaterationFunction function;
	protected final LeastSquaresOptimizer leastSquaresOptimizer;

	protected final static int MAXNUMBEROFITERATIONS = 1000000;

	public NonLinearLeastSquaresSolver(TrilaterationFunction function, LeastSquaresOptimizer leastSquaresOptimizer) {
		this.function = function;
		this.leastSquaresOptimizer = leastSquaresOptimizer;
	}

	public Optimum solve(double[] target, double[] weights, double[] initialPoint, boolean debugInfo) {
		if (debugInfo) {
			System.out.println("Max Number of Iterations : " + MAXNUMBEROFITERATIONS);
		}

		LeastSquaresProblem leastSquaresProblem = LeastSquaresFactory.create(
				function,
				new ArrayRealVector(target, false),
				new ArrayRealVector(initialPoint, false),
				new DiagonalMatrix(weights),
				null,
				MAXNUMBEROFITERATIONS,
				MAXNUMBEROFITERATIONS);

		return leastSquaresOptimizer.optimize(leastSquaresProblem);
	}

	public Optimum solve(double[] target, double[] weights, double[] initialPoint) {
		return solve(target, weights, initialPoint, false);
	}

	public Optimum solve(boolean debugInfo) {
		int numberOfPositions = function.getPositions().length;
		int positionDimension = function.getPositions()[0].length;

		double[] initialPoint = new double[positionDimension];
		// initial point, use average of the vertices
		for (int i = 0; i < function.getPositions().length; i++) {
			double[] vertex = function.getPositions()[i];
			for (int j = 0; j < vertex.length; j++) {
				initialPoint[j] += vertex[j];
			}
		}
		for (int j = 0; j < initialPoint.length; j++) {
			initialPoint[j] /= numberOfPositions;
		}

		if (debugInfo) {
			StringBuilder output = new StringBuilder("initialPoint: ");
			for (int i = 0; i < initialPoint.length; i++) {
				output.append(initialPoint[i]).append(" ");
			}
			System.out.println(output.toString());
		}

		double[] target = new double[numberOfPositions];
		double[] distances = function.getDistances();
		double[] weights = new double[target.length];
		for (int i = 0; i < target.length; i++) {
			target[i] = 0.0;
			weights[i] = inverseSquareLaw(distances[i]);
		}

		return solve(target, weights, initialPoint, debugInfo);
	}

	public static Point2D.Double getLocationByTrilateration(
			Point2D.Double ponto1, double distance1,
			Point2D.Double ponto2, double distance2,
			Point2D.Double ponto3, double distance3){

		//DECLARACAO DE VARIAVEIS
		Point2D.Double retorno = new Point2D.Double();
		double[] P1   = new double[2];
		double[] P2   = new double[2];
		double[] P3   = new double[2];
		double[] ex   = new double[2];
		double[] ey   = new double[2];
		double[] p3p1 = new double[2];
		double jval  = 0;
		double temp  = 0;
		double ival  = 0;
		double p3p1i = 0;
		double triptx;
		double tripty;
		double xval;
		double yval;
		double t1;
		double t2;
		double t3;
		double t;
		double exx;
		double d;
		double eyy;

		//TRANSFORMA OS PONTOS EM VETORES
		//PONTO 1
		P1[0] = ponto1.getX();
		P1[1] = ponto1.getY();
		//PONTO 2
		P2[0] = ponto2.getX();
		P2[1] = ponto2.getY();
		//PONTO 3
		P3[0] = ponto3.getX();
		P3[1] = ponto3.getY();

		//TRANSFORMA O VALOR DE METROS PARA A UNIDADE DO MAPA
		//DISTANCIA ENTRE O PONTO 1 E A MINHA LOCALIZACAO
		distance1 = (distance1 / 100000);
		//DISTANCIA ENTRE O PONTO 2 E A MINHA LOCALIZACAO
		distance2 = (distance2 / 100000);
		//DISTANCIA ENTRE O PONTO 3 E A MINHA LOCALIZACAO
		distance3 = (distance3 / 100000);

		for (int i = 0; i < P1.length; i++) {
			t1   = P2[i];
			t2   = P1[i];
			t    = t1 - t2;
			temp += (t*t);
		}
		d = Math.sqrt(temp);
		for (int i = 0; i < P1.length; i++) {
			t1    = P2[i];
			t2    = P1[i];
			exx   = (t1 - t2)/(Math.sqrt(temp));
			ex[i] = exx;
		}
		for (int i = 0; i < P3.length; i++) {
			t1      = P3[i];
			t2      = P1[i];
			t3      = t1 - t2;
			p3p1[i] = t3;
		}
		for (int i = 0; i < ex.length; i++) {
			t1 = ex[i];
			t2 = p3p1[i];
			ival += (t1*t2);
		}
		for (int  i = 0; i < P3.length; i++) {
			t1 = P3[i];
			t2 = P1[i];
			t3 = ex[i] * ival;
			t  = t1 - t2 -t3;
			p3p1i += (t*t);
		}
		for (int i = 0; i < P3.length; i++) {
			t1 = P3[i];
			t2 = P1[i];
			t3 = ex[i] * ival;
			eyy = (t1 - t2 - t3)/Math.sqrt(p3p1i);
			ey[i] = eyy;
		}
		for (int i = 0; i < ey.length; i++) {
			t1 = ey[i];
			t2 = p3p1[i];
			jval += (t1*t2);
		}
		xval = (Math.pow(distance1, 2) - Math.pow(distance2, 2) + Math.pow(d, 2))/(2*d);
		yval = ((Math.pow(distance1, 2) - Math.pow(distance3, 2) + Math.pow(ival, 2) + Math.pow(jval, 2))/(2*jval)) - ((ival/jval)*xval);

		t1 = ponto1.getX();
		t2 = ex[0] * xval;
		t3 = ey[0] * yval;
		triptx = t1 + t2 + t3;
		t1 = ponto1.getY();
		t2 = ex[1] * xval;
		t3 = ey[1] * yval;
		tripty = t1 + t2 + t3;

		retorno.setLocation(triptx,tripty);
		return retorno;
	}

	private double inverseSquareLaw(double distance) {
		return 1 / (distance * distance);
	}

	public Optimum solve() {
		return solve(false);
	}
}
