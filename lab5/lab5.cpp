#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
void main() {
	srand(time(NULL));
	printf("Matrix of adjancency\n\n");
	int matrix[5][5];
	int matrixInc[5][10];
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
	int col=0, row = 0;
	for (int i = 0; i < 5; i++) {
		for (int j =i; j < 5; j++)
		{
			if (matrix[i][j] == 1) {
				matrixInc[row+i][col] = 1;
				matrixInc[row+j][col] = 1;
				col++;
				if (col == matrixSize) {
					i = 5;
					j = 5;
				}
			}
		}
	}
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < matrixSize; j++)
		{
			if (matrixInc[i][j] != 1) {
				matrixInc[i][j] = 0;
			}
		}
	}
	printf("\n   ");
	char string[20] = "A B C D E F G H I J";
	for (int i = 0; i < 2 * matrixSize; i++) {
		printf("%c", string[i]);
	}
	printf("\n\n");
	for (int i = 0; i < 5; i++) {
		printf("%d  ", i + 1);
		for (int j = 0; j < matrixSize; j++)
		{
			printf("%d ", matrixInc[i][j]);
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