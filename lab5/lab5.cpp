#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void main() {
	srand(time(NULL));
	int matrix[5][5];
	int matrixSize = 0;
	int degree = 0;
	for (int i = 0; i < 5; i++) {
		for (int j = i; j < 5; j++) {
			matrix[i][j] = rand() % 2;
			matrix[j][i] = matrix[i][j];
			if (i == j)matrix[i][i] = 0;
			if (matrix[i][j] == 1) {
				matrixSize++;
			}
		}

	}
	printf("   1 2 3 4 5\n\n");
	for (int i = 0; i < 5; i++) {
		printf("%d  ", i + 1);
		for (int j = 0; j < 5; j++) {
			printf("%d ", matrix[i][j]);
		}
		printf("\n");
	}
	printf("\nMatrix size = %d\n\n", matrixSize);
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++) {
			if (matrix[i][j] == 1) {
				degree++;
			}
		}
		if (degree == 0) printf("%d is an isolated vertex\n", i + 1);
		if (degree == 1) printf("%d is a final vertex\n", i + 1);
		if(5-degree==1)printf("%d is a dominated vertex\n", i+1);
		degree = 0;
	}
	matrixSize = 0;
	////////
	int maxOne = 2;
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++) {
			matrix[j][i] = rand() % 2;
			if (matrix[j][i] == 1) {
				if (maxOne > 0) maxOne--;
				else matrix[j][i] = 0;
			}
			if (j == 4 && maxOne == 1) matrix[j][i] = 1;
			if (matrix[i][j] == 1) {
				matrixSize++;
			}
		}
		maxOne = 2;
	}
	printf("\n\n   a b c d e\n\n");
	for (int i = 0; i < 5; i++) {
		printf("%d  ", i + 1);
		for (int j = 0; j < 5; j++) {
			printf("%d ", matrix[i][j]);
		}
		printf("\n");
	}
	printf("\nMatrix size = %d\n\n", matrixSize);
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++) {
			if (matrix[i][j] == 1) {
				degree++;
			}
		}
		if (degree == 0) printf("%d is an isolated vertex\n", i + 1);
		if (degree == 1) printf("%d is a final vertex\n", i + 1);
		if (5 - degree == 1)printf("%d is a dominated vertex\n", i + 1);
		degree = 0;
	}
}