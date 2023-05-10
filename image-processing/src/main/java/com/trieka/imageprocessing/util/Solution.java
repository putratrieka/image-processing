package com.trieka.imageprocessing.util;

import java.util.HashMap;
import java.util.Map;

class Solution {
	public static void main(String[] args) {
		String[] ss = { "A", "A", "B", "C", "A", "B", "B", "A", "A" };
		soulution1(ss);

		Point p1 = new Point(2, 4);
		Point p2 = new Point(3, 4);
		Point p3 = new Point(3, 3);
		Point p4 = new Point(3, 5);
		Point[] poins = { p1, p2, p3, p4 };
		soulution2(poins, 3, 5, 1);
	}

	public static void soulution1(String[] stringArr) {
		Map<String, Integer> map = new HashMap<>();
		for (String s : stringArr) {
			if (map.get(s) == null) {
				map.put(s, 1);
				continue;
			}

			map.put(s, map.get(s) + 1);
			if (map.get(s) >= 3) {
				System.out.println(s);
				break;
			}

		}
	}

	public static void soulution2(Point[] poins, int r, int centerX, int centerY) {
		Point maxPoint = null;
		double max = Integer.MIN_VALUE;

		for (Point p : poins) {
			double distance = calculateDistance(p, centerX, centerY);
			if (distance > r) {
				// the point is outside of the circle
				continue;
			}

			// the point is inside the circle, find the max point
			if ((p.x * p.y) > max) {
				max = p.x * p.y;
				maxPoint = p;
			}
		}
		System.out.println("Maximum Point is x:" + maxPoint.x + ", y:" + maxPoint.y);

	}

	private static double calculateDistance(Point p, int centerX, int centerY) {
		double argA = Math.pow((p.x - centerX), 2);
		double argB = Math.pow((p.y - centerY), 2);

		return Math.sqrt(argA + argB);
	}

	public static class Point {
		public double x;
		public double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

	}
}