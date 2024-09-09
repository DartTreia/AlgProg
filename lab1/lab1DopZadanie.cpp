#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <malloc.h>
#include <string.h>

bool strEqual(char str1[20],char str2[20]);
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
		arr2[i] = rand() % 120-40;
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

	//5 задание
	printf("\n\n---|5th task|-------------------------- \n");
	int choice = 0;
	char temp[20];
	int crsNum = 0;
	struct student
	{
		char secondName[20];
		char firstName[20];
		char thirdName[20];
		char faculty[20];
		int course;
	};
	/*struct student students[5] = {"Monin\0","Ivan\0","Alekseevich\0","FVT\0",2,
									"Kokarev\0","Danila\0","Sergeevich\0","FVT\0",2,
									"Konkin\0","Dmitriy\0","Stanislavovich\0","FVT\0",2,
									"Ivanov\0","Ivan\0","Alekseevich\0","Econom\0",1,
									"Ivanov\0","Danila\0","Petrovich\0","FVT\0",4 };*/
	
									
		//Задание на практике
	struct student* students;
	int k = 0;

	students = (struct student*)malloc(sizeof(struct student));
	while(1){
		printf("\n\n%d Student\n", k + 1);
		printf("Write down second name: ");
		getchar();
		fgets(students[k].secondName, sizeof(students[k].secondName), stdin);
		students[k].secondName[strcspn(students[k].secondName, "\n")] = '\0';
		if (students[k].secondName[0] == '@') {
			break;
		}
		printf("Write down first name: ");
		fgets(students[k].firstName, sizeof(students[k].firstName), stdin);
		students[k].firstName[strcspn(students[k].firstName, "\n")] = '\0';
		printf("Write down third name: ");
		fgets(students[k].thirdName, sizeof(students[k].thirdName), stdin);
		students[k].thirdName[strcspn(students[k].thirdName, "\n")] = '\0';
		printf("Write down faculty: ");
		fgets(students[k].faculty, sizeof(students[k].faculty), stdin);
		students[k].faculty[strcspn(students[k].faculty, "\n")] = '\0';
		printf("Write down course: ");
		scanf_s("%d", &students[k].course);
		k+=2;
		struct student* temp = (struct student*)realloc(students, k * sizeof(struct student));
		students = temp;
		k--;
	} 

	printf("SecondName   FirstName   ThirdName   Faculty   Course\n");
	for (int i = 0; i < k; i++) {
		if (students[i].secondName[0] == '@')break;
		printf("%8s %11s %15s %4s %8d\n", students[i].secondName, students[i].firstName, students[i].thirdName, students[i].faculty, students[i].course);
	}
	printf("\n\nTap requiring number\nSearching by: \n");
	printf("Second name - 1\nFirst name - 2\nFaculty - 3\nCourse - 4\n");
	scanf_s("%d", &choice);
	switch (choice) {
		case 1:
			printf("Write down Second name: ");
			getchar();
			fgets(temp,20,stdin);
			for (int i = 0; i < k; i++) {
				if (strEqual(temp,students[i].secondName)) {
					printf("%8s %11s %15s %4s %8d\n", students[i].secondName, students[i].firstName, students[i].thirdName, students[i].faculty, students[i].course);
				}
			}
			break;
		case 2:
			printf("Write down First name: ");
			getchar();
			fgets(temp, 20, stdin);
			for (int i = 0; i < k; i++) {
				if (strEqual(temp, students[i].firstName)) {
					printf("%8s %11s %15s %4s %8d\n", students[i].secondName, students[i].firstName, students[i].thirdName, students[i].faculty, students[i].course);
				}
			}
			break;
		case 3:
			printf("Write down Faculty: ");
			getchar();
			fgets(temp, 20, stdin);
			for (int i = 0; i < k; i++) {
				if (strEqual(temp, students[i].faculty)) {
					printf("%8s %11s %15s %4s %8d\n", students[i].secondName, students[i].firstName, students[i].thirdName, students[i].faculty, students[i].course);
				}
			}
			break;
		case 4:
			printf("Write down Course: ");
			scanf_s("%d", &crsNum);
			for (int i = 0; i < k; i++) {
				if (crsNum == students[i].course) {
					printf("%8s %11s %15s %4s %8d\n", students[i].secondName, students[i].firstName, students[i].thirdName, students[i].faculty, students[i].course);
				}
			}
			break;
	}
}

bool strEqual(char str1[20], char str2[20]) {
	for (int i = 0; i < 20; i++) {
		if (str1[i] == '\n' && str2[i] == '\0') return true;
		if (str1[i] != str2[i]) return false;
	}
	return true;
}