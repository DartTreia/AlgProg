#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <malloc.h>

void main() {
	srand(time(NULL));
	//1 задание
	printf("---|1st task|-------------------------- \n");
	printf("Array: ");
	int arr[10];
	for (int i = 0; i < 10; i++) {
		arr[i] = (i * 20+7) % 12;
		printf("%d ", arr[i]);
	}
	int min = arr[0];
	int max = arr[0];
	for (int i = 0; i < 10; i++) {
		if (arr[i] > max) max = arr[i];
		if (arr[i] < min) min = arr[i];
	}
	printf("\nDifference between max and min: %d\n\n", max - min);

	//2 задание 
	printf("---|2nd task|-------------------------- \n");
	printf("Array: ");
	int arr2[10];
	for (int i = 0; i < 10; i++) {
		arr2[i] = rand() % 100 - 50;
		printf("%d ", arr2[i]);
	}

	//3 задание
	printf("\n\n---|3rd task|-------------------------- \n");
	int* arr3;
	int sizeArr3;
	printf("Enter array's size: ");
	scanf_s("%d", &sizeArr3);

	arr3 = (int*)malloc(sizeArr3 * sizeof(int));
	for (int i = 0; i < sizeArr3; i++) {
		*arr3 = rand() % 100 - 50;
		printf("%d ", *arr3);
		arr3++;
	}

	//4 задание
	printf("\n\n---|4th task|-------------------------- \n");
	printf("Array:\n");
	int arr4[10][10];
	int sum = 0;
	int sum2 = 0;//Для главной диагонали
	for (int i = 0; i < 10; i++) {
		for (int j = 0; j < 10; j++) {
			arr4[i][j] = rand() % 100 - 50;
			sum += arr4[i][j];
			printf("%3d ", arr4[i][j]);
			if (i == j) {
				sum2 += arr4[i][j];
			}
		}
		printf("  =  %d\n",sum);
		sum = 0;
	}
	for(int i=0;i<10;i++) printf("  ||", sum);
	printf("  \\\n");
	for (int j = 0; j < 10; j++) {
		for (int i = 0; i < 10; i++) {
			sum += arr4[i][j];
		}
		printf("%3d ", sum);
		sum = 0;
	}
	printf("  %d\n", sum2);
}