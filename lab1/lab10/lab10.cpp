#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <windows.h>
#include <time.h>
#include <stdlib.h>
#include <locale.h>
#include <conio.h>

typedef struct Node {
	int n;
	Node* next;
};

Node* G;

Node* make_G(int nv, int** v);
void merge_G(int nv, Node* G, int del);
void split_G(int nv, Node* G);
int find_G(Node* p, int n);
void add_G(Node* p, int n);
int del_G(Node* p, int n);
void print_G(int nv, Node* G);

Node* make_G(int nv, int** v) {
	Node* G = (Node*)malloc(sizeof(Node) * nv);
	for (int i = 0; i < nv; i++) {
		G[i].n = i;
		G[i].next = nullptr;
		Node* p = &G[i];

		for (int j = 0; j < nv; j++) {
			if (v[i][j] == 1) {
				p->next = (Node*)malloc(sizeof(Node));
				p = p->next;
				p->n = j;
				p->next = nullptr;
			}
		}
	}
	return G;
}

//Объединение двух вершин графа в одну новую вершину
void merge_G(int nv, Node* G, int del) {
	//del==1 - стягивание, del==0 - отождествление
	int n1, n2;
	if (del)
		printf("Стягивание ребра\n");
	else
		printf("Отождествление вершин\n");
	//Ввод номеров вершин 1 и 2
	while (1) {
		printf("Введите номер вершины 1: ");
		scanf("%d", &n1);
		if ((n1 >= 0) && (n1 < nv - 1)) break;
	}
	while (1) {
		printf("Введите номер вершины 2: ");
		scanf("%d", &n2);
		if ((n2 >= 0) && (n2 < nv - 1) && (n2 != n1)) break;
	}
	//Удаление ребра
	if (del) {
		del_G(&G[n1], n2); //Удалить связь n1 -> n2
		del_G(&G[n2], n1);
	}
	//Создание списка для новой вершины
	for (int i = 0; i < nv - 1; i++) {
		if (find_G(&G[n1], i) || find_G(&G[n2], i)) {			//Есть связь вершины n1 или n2 с вершиной i
			add_G(&G[nv - 1], i);									//Добавляем вершину i в список новой вершины
		}
	}
	//Удаление вершины 1
	G[n1].n = -1;
	G[n1].next = NULL;
	//Удаление вершины 2
	G[n2].n = -1;
	G[n2].next = NULL;
	//Удаление вершин 1 и 2 в списках
	for (int i = 0; i < nv; i++) {
		int f = del_G(&G[i], n1) + del_G(&G[i], n2);			//Удаление вершин n1 и n2 из списка i
		if (f > 0)													//Если связь с n1 или n2 существовала
			add_G(&G[i], nv - 1);									//добавление новой вершины nv-1
	}
}

void split_G(int nv, Node* G) {
	int n1; //Вершина n1 расщепляется на две: n1 и nv - 1, nv - текущее кол-во вершин
	printf("Расщепление вершины\n");
	//Ввод номера вершины
	while (1) {
		printf("Введите номер вершины: ");
		scanf("%d", &n1);
		if ((n1 >= 0) && (n1 < nv - 1)) break;
	}
	//Создание списка для новой вершины
	for (int i = 0; i < nv - 1; i++) {
		if (find_G(&G[n1], i)) {									//Есть связь вершины n1 с вершиной i
			if (n1 != i)											//Не петля
				add_G(&G[nv - 1], i);								//Добавляем вершину i в список новой вершины
			else													//Петля
				add_G(&G[nv - 1], nv - 1);							//Добавляем петлю в список новой вершины
		}
	}

	//Добавление связи между вершинами: вершины n1 и nv - 1 соединяются ребром
	add_G(&G[n1], nv - 1); //Добавляем ребро между старой вершиной n1 и новой вершиной nv - 1
	add_G(&G[nv - 1], n1);
}

int find_G(Node* p, int n) {
	while (p->next != NULL) {
		if (p->next->n == n)
			return 1;
		p = p->next;
	}
	return 0;
}

void add_G(Node* p, int n) {
	while (p->next != nullptr) {
		p = p->next;
	}
	p->next = (Node*)malloc(sizeof(Node));
	p = p->next;
	p->n = n;
	p->next = nullptr;
}

int del_G(Node* p, int n) {
	while (p->next != nullptr) {
		if (p->next->n == n) {
			p->next = p->next->next;
			return 1;
		}
		p = p->next;
	}
	return 0;
}

void print_G(int nv, Node* G) {
	Node* p;
	printf("Списки смежности:\n");
	for (int i = 0; i < nv; i++) {
		p = &G[i];
		if (p->n != -1) {
			while (p != NULL) {
				printf("%d > ", p->n);
				p = p->next;
			}
			printf("\n");
		}
	}
}

void print_matrix(int size, int** arr) {
	printf("     ");
	for (int i = 0; i < size; i++) {
		printf("%4d ", i);
	}
	printf("\n");
	printf("-----------------------------------------------------------\n");

	for (int i = 0; i < size; i++) {
		printf("%4d|", i);
		for (int j = 0; j < size; j++) {
			printf("%4d ", arr[i][j]);
		}
		printf("\n");
	}
	printf("\n");
}

void peres(int** a1, int** a2, int** a3, int si1, int si2) {
	for (int i = 0; i < si1; i++) {
		for (int j = 0; j < si1; j++) {
			a3[i][j] = a1[i][j] && a2[i][j]; //вычисление через логическое и 
		}
	}
	print_matrix(si1, a3);

	for (int i = 0; i < si1; ++i) free(a3[i]);
	free(a3);

}

void obed(int** a1, int** a2, int** a3, int si1, int si2) {
	for (int i = 0; i < si2; i++) {
		for (int j = 0; j < si2; j++) {
			a3[i][j] = 0;
		}
	}

	for (int i = 0; i < si1; i++) {
		for (int j = 0; j < si1; j++) {
			a3[i][j] = a1[i][j] | a2[i][j]; //побитовое объединение
		}
	}

	//заполнение доп областей матрицы a3,значениями из матрицы a2 для индексов,которые выходят за пределы si1
	for (int i = 0; i < si2; i++) {
		for (int j = 0; j < si2; j++) {

			if ((i >= si1) && (j >= si1)) {
				a3[i][j] = a2[i][j];
			}

			else if (i >= si1) {
				a3[i][j] = a2[i][j];
			}

			else if (j >= si1) {
				a3[i][j] = a2[i][j];
			}
		}
	}
	print_matrix(si2, a3);

	for (int i = 0; i < si2; ++i) free(a3[i]);
	free(a3);
	//заполняет расширенные области матрицы значениями из a2
}

void otoz(int** a, int** arr, int size, int b1, int b2) {
	for (int i = 0; i < size; i++) {					//склеиваем введенные первые и вторые строку и столбец с записью в первый
		for (int j = 0; j < size; j++) {
			arr[i][b1] = arr[i][b1] | arr[i][b2]; //объединение элементов в строке через побитовое или
			//printf("arr[%d][%d] = %d\n", i, b1, arr[i][b1]);
			arr[b1][j] = arr[b1][j] | arr[b2][j]; //объединение элементов в столбце
			//printf("arr[%d][%d] = %d\n", b1, j, arr[b1][j]);
		}
	}

	for (int i = 0; i < size - 1; i++) {				//заполнение нового массива "a" значениями
		for (int j = 0; j < size - 1; j++) {
			if ((i < b2) && (j < b2)) {
				a[i][j] = arr[i][j];
			}

			else if ((i >= b2) && (j >= b2)) {
				a[i][j] = arr[i + 1][j + 1];
			}

			else if (i >= b2) {
				a[i][j] = arr[i + 1][j];
			}

			else if (j >= b2) {
				a[i][j] = arr[i][j + 1];
			}
		}
	}
	//слияние двух строк и столбцов в матрице,затем создание новой матрицы, в которой одна строка и один столбец (соответствующие индексу b2) удалены
}

void kol_sum(int** a1, int** a2, int** a3, int si1, int si2) {
	int g = 0;

	for (int i = 0; i < si2; i++) {
		for (int j = 0; j < si2; j++) {
			a3[i][j] = 0;
		}
	}

	for (int i = 0; i < si1; i++) {
		for (int j = 0; j < si1; j++) {
			a3[i][j] = a1[i][j] ^ a2[i][j]; //сложение через исключающее или
		}
	}

	//проверка на выход за пределы области матрицы
	for (int i = 0; i < si2; i++) {
		for (int j = 0; j < si2; j++) {

			if ((i >= si1) && (j >= si1)) {
				a3[i][j] = a2[i][j];
			}

			else if (i >= si1) {
				a3[i][j] = a2[i][j];
			}

			else if (j >= si1) {
				a3[i][j] = a2[i][j];
			}
		}
	}
	print_matrix(si2, a3);
	//поиск изолированных вершин
	int k = 0;
	int* mass;
	mass = (int*)malloc(sizeof(int) * si2);

	for (int i = 0; i < si2; i++) {
		mass[i] = 0;
	}

	for (int i = 0; i < si2; i++) {
		for (int j = 0; j < si2; j++) {
			if (a3[i][j] == 1) {
				g++;
			}
		}

		if (g == 0) {
			mass[i] = 1;
			k++;
			printf("Вершина %d - изолирована\n", i);

		}
		g = 0;
	}

	printf("Массив изолированных вершин: \n");
	for (int i = 0; i < si2; i++) {
		printf("%5d", mass[i]);
	}
	printf("\n\n");
	//printf("k = %d\n", k);

	//вывод матрицы без изолированных вершин
	for (int i = 0; i < si2; i++) {
		if (mass[i] == 0) {
			for (int j = 0; j < si2; j++) {
				if (mass[j] == 0) {
					printf("%5d", a3[i][j]);
				}
			}
			printf("\n");
		}
	}

	//если все вершины изолированные
	if (k == si2) {
		printf("Самоуничтожение в результате кольцевой суммы...");
	}
}

void create_matrix(int size, int** arr) {
	for (int i = 0; i < size; i++) {
		for (int j = i; j < size; j++) {
			if (i == j) {
				arr[i][j] = 0;
			}
			else {					//ниже главной диагонали отразить то, что выше
				arr[i][j] = rand() % 2;
				arr[j][i] = arr[i][j];
			}
		}
	}

}

Node* intersect_Graphs(int nv, Node* G1, Node* G2) {
	Node* G = (Node*)malloc(sizeof(Node) * nv);

	for (int i = 0; i < nv; i++) {
		G[i].n = i;
		G[i].next = NULL;
		Node* p = &G[i];

		Node* p1 = G1[i].next;
		while (p1 != NULL) {
			Node* p2 = G2[i].next;
			while (p2 != NULL) {
				if (p1->n == p2->n) {
					p->next = (Node*)malloc(sizeof(Node));
					p = p->next;
					p->n = p1->n;
					p->next = NULL;
					break;
				}
				p2 = p2->next;
			}
			p1 = p1->next;
		}
	}

	return G;
}

Node* union_Graphs(int nv1, Node* G1, int nv2, Node* G2) {
	int nv = nv1 + nv2;
	Node* G = (Node*)malloc(sizeof(Node) * nv);

	// Копируем первый граф
	for (int i = 0; i < nv1; i++) {
		Node* current = &G1[i];
		G[i].n = current->n;
		G[i].next = NULL;

		Node* p = &G[i];
		current = current->next;
		while (current != NULL) {
			p->next = (Node*)malloc(sizeof(Node));
			p = p->next;
			p->n = current->n;
			p->next = NULL;
			current = current->next;
		}
	}

	// Копируем второй граф
	for (int i = 0; i < nv2; i++) {
		Node* current = &G2[i];
		G[nv1 + i].n = current->n + nv1; // Смещение индексов
		G[nv1 + i].next = NULL;

		Node* p = &G[nv1 + i];
		current = current->next;
		while (current != NULL) {
			p->next = (Node*)malloc(sizeof(Node));
			p = p->next;
			p->n = current->n + nv1;
			p->next = NULL;
			current = current->next;
		}
	}

	return G;
}

// Функция для удаления ребра между двумя вершинами
void remove_edge(Node* G, int u, int v) {
	Node* temp = &G[u];
	Node* prev = NULL;

	// Удаляем v из списка u
	while (temp != NULL && temp->n != v) {
		prev = temp;
		temp = temp->next;
	}
	if (temp != NULL) {
		if (prev == NULL) {
			G[u].next = temp->next;
		}
		else {
			prev->next = temp->next;
		}
		free(temp);
	}

	// Удаляем u из списка v
	temp = &G[v];
	prev = NULL;
	while (temp != NULL && temp->n != u) {
		prev = temp;
		temp = temp->next;
	}
	if (temp != NULL) {
		if (prev == NULL) {
			G[v].next = temp->next;
		}
		else {
			prev->next = temp->next;
		}
		free(temp);
	}
}

Node* merge_two_matrices(int nv1, int** matrix1, int nv2, int** matrix2) {
	int total_vertices = nv1 + nv2; // Общее количество вершин
	Node* G = (Node*)malloc(sizeof(Node) * total_vertices);

	// Инициализация списка смежности
	for (int i = 0; i < total_vertices; i++) {
		G[i].n = i;
		G[i].next = NULL;
	}

	// Заполняем список из первой матрицы
	for (int i = 0; i < nv1; i++) {
		Node* current = &G[i];
		for (int j = 0; j < nv1; j++) {
			if (matrix1[i][j] == 1) {
				current->next = (Node*)malloc(sizeof(Node));
				current = current->next;
				current->n = j;
				current->next = NULL;
			}
		}
	}

	// Заполняем список из второй матрицы (со сдвигом на nv1)
	for (int i = 0; i < nv2; i++) {
		Node* current = &G[nv1 + i];  // Смещаем индексы
		for (int j = 0; j < nv2; j++) {
			if (matrix2[i][j] == 1) {
				current->next = (Node*)malloc(sizeof(Node));
				current = current->next;
				current->n = nv1 + j; // Смещаем индексы для второй матрицы
				current->next = NULL;
			}
		}
	}

	return G;
}

// Функция для соединения двух графов
Node* join_Graphs(int nv1, Node* G1, int nv2, Node* G2) {
	int new_nv = nv1 + nv2;
	Node* joinedG = (Node*)malloc(sizeof(Node) * new_nv);

	// Копируем первый граф в соединенный граф
	for (int i = 0; i < nv1; i++) {
		joinedG[i].n = G1[i].n;
		joinedG[i].next = NULL;

		Node* current = &joinedG[i];
		Node* original = G1[i].next;
		while (original) {
			current->next = (Node*)malloc(sizeof(Node));
			current = current->next;
			current->n = original->n;
			current->next = NULL;
			original = original->next;
		}
	}

	// Копируем второй граф в соединенный граф
	for (int i = 0; i < nv2; i++) {
		joinedG[nv1 + i].n = nv1 + G2[i].n;
		joinedG[nv1 + i].next = NULL;

		Node* current = &joinedG[nv1 + i];
		Node* original = G2[i].next;
		while (original) {
			current->next = (Node*)malloc(sizeof(Node));
			current = current->next;
			current->n = nv1 + original->n;
			current->next = NULL;
			original = original->next;
		}
	}

	// Добавляем ребра между всеми вершинами первого и второго графа
	for (int i = 0; i < nv1; i++) {
		for (int j = 0; j < nv2; j++) {
			Node* current = &joinedG[i];
			while (current->next) {
				current = current->next;
			}
			current->next = (Node*)malloc(sizeof(Node));
			current = current->next;
			current->n = nv1 + j;
			current->next = NULL;
		}
	}

	for (int i = 0; i < nv2; i++) {
		for (int j = 0; j < nv1; j++) {
			Node* current = &joinedG[nv1 + i];
			while (current->next) {
				current = current->next;
			}
			current->next = (Node*)malloc(sizeof(Node));
			current = current->next;
			current->n = j;
			current->next = NULL;
		}
	}

	return joinedG;
}

void free_Graph(int nv, Node* G) {
	for (int i = 0; i < nv; i++) {
		Node* current = G[i].next;
		while (current != NULL) {
			Node* tmp = current;
			current = current->next;
			free(tmp);
		}
	}
	free(G);
}


int main() {
	srand(time(NULL));
	setlocale(LC_ALL, "");
	char menu;

	do {
		system("cls");
		printf("********************************************************************************\n");
		printf("\t\t\t\tМЕНЮ:\n");
		printf("[1 - Задание 1: генерация двух матриц смежности и вывод на экран\n	       матрица смежности => список смежности]\n\n");
		printf("[2 - Задание 2.1: операции отождествления вершин, стягивания ребра, расщепления вершины для матричной формы]\n\n");
		printf("[3 - Задание 3: операции объединения, пересечeния, кольцевой суммы для матричной формы]\n\n");
		printf("[4 - Задание 4: операция декартова произведения для матричной формы]\n\n");
		printf("[5 - Задание 2.2: операции отождествления вершин, стягивания ребра, расщепления вершины для списков смежности]\n\n");
		printf("ESC - выход из программы\n\n");
		printf("********************************************************************************\n");

		menu = _getch();

		switch (menu) {			//1
		case '1':
			system("cls");

			int size1, size2;
			int** arr1;
			int** arr2;

			printf("Введите размер матрицы смежности M1: ");
			scanf("%d", &size1);
			printf("\n");

			printf("Введите размер матрицы смежности M2: ");
			scanf("%d", &size2);
			printf("\n");

			arr1 = (int**)malloc(sizeof(int*) * size1);
			for (int i = 0; i < size1; i++) {
				arr1[i] = (int*)malloc(sizeof(int) * size1);
			}

			arr2 = (int**)malloc(sizeof(int*) * size2);
			for (int i = 0; i < size2; i++) {
				arr2[i] = (int*)malloc(sizeof(int) * size2);
			}

			printf("Матрица M1:\n");
			create_matrix(size1, arr1);
			print_matrix(size1, arr1);

			printf("Матрица M2:\n");
			create_matrix(size2, arr2);
			print_matrix(size2, arr2);

			printf("Список смежности S1:\n");
			G = make_G(size1 + 1, arr1);
			print_G(size1, G);
			printf("\n");

			printf("Список смежности S2:\n");
			G = make_G(size2 + 1, arr2);
			print_G(size2, G);

			for (int i = 0; i < size1; ++i) free(arr1[i]);
			free(arr1);
			for (int i = 0; i < size2; ++i) free(arr2[i]);
			free(arr2);

			_getch();
			break;
		}

		switch (menu) {			//2
		case '2':
			system("cls");
			//printf("2");
			int size1;
			int** arr1;
			int** arr2;
			int** arr3;
			//int number;

			printf("Введите размер матрицы смежности M1: ");
			scanf("%d", &size1);
			printf("\n");

			arr1 = (int**)malloc(sizeof(int*) * size1);
			for (int i = 0; i < size1; i++) {
				arr1[i] = (int*)malloc(sizeof(int) * size1);
			}

			arr2 = (int**)malloc(sizeof(int*) * size1);
			for (int i = 0; i < size1; i++) {
				arr2[i] = (int*)malloc(sizeof(int) * size1);
			}

			arr3 = (int**)malloc(sizeof(int*) * size1);
			for (int i = 0; i < size1; i++) {
				arr3[i] = (int*)malloc(sizeof(int) * size1);
			}

			printf("Матрица M1:\n");
			create_matrix(size1, arr1);
			print_matrix(size1, arr1);

			memcpy(arr2, arr1, sizeof(int) * size1 * size1);
			memcpy(arr3, arr1, sizeof(int) * size1 * size1);

			while (1) {
				printf("Введите Q(q), W(w), E(e) для отождествления, стягивания ребра и расщепления\n");
				int k = _getch();

				if ((k == 'Q') || (k == 'q')) {
					printf("===================================================================\n");
					//отождествление
					int s1, s2;
					printf("Отождествление вершин графа M1\n\n");
					printf("Введите первую вершину: ");
					scanf("%d", &s1);

					printf("Введите вторую вершину: ");
					scanf("%d", &s2);
					printf("\n");

					int** a1;
					a1 = (int**)malloc(sizeof(int*) * size1 - 1);
					for (int i = 0; i < size1 - 1; i++) {
						a1[i] = (int*)malloc(sizeof(int) * size1 - 1);
					}

					otoz(a1, arr1, size1, s1, s2);

					printf("Отождествление вершин %d и %d для M1:\n", s1, s2);
					print_matrix(size1 - 1, a1);

					for (int i = 0; i < size1 - 1; ++i) free(a1[i]);
					free(a1);
				}

				else if ((k == 'W') || (k == 'w')) {
					printf("===================================================================\n");
					//стягивание
					int s3, s4;
					printf("Стягивание графа M1\n\n");
					printf("Введите первую вершину: ");
					scanf("%d", &s3);

					printf("Введите вторую вершину: ");
					scanf("%d", &s4);
					printf("\n");

					int** a2;
					a2 = (int**)malloc(sizeof(int*) * size1 - 1);
					for (int i = 0; i < size1 - 1; i++) {
						a2[i] = (int*)malloc(sizeof(int) * size1 - 1);
					}
					if (arr2[s3][s4] == 0) {
						printf("Ребра не существует\n\n");
					}
					else {
						otoz(a2, arr2, size1, s3, s4);

						for (int i = 0; i < size1 - 1; i++) {
							for (int j = 0; j < size1 - 1; j++) {
								if (i == j) {
									a2[i][j] = 0;
								}
							}
						}
						printf("Стягивание ребра между вершинами %d и %d для M1:\n", s3, s4);
						print_matrix(size1 - 1, a2);
					}
					for (int i = 0; i < size1 - 1; ++i) free(a2[i]);
					free(a2);
				}
				else if ((k == 'E') || (k == 'e')) {
					printf("===================================================================\n");
					//расщепление
					int s5;
					printf("Расщепление вершины графа M1\n\n");
					printf("Введите вершину для расщепления: ");
					scanf("%d", &s5);
					printf("\n");
					int sss = size1 + 1;

					int** a3;
					a3 = (int**)malloc(sizeof(int*) * sss);
					for (int i = 0; i < sss; i++) {
						a3[i] = (int*)malloc(sizeof(int) * sss);
					}
					for (int i = 0; i < sss; i++) {
						for (int j = 0; j < sss; j++) {
							a3[i][j] = 0;
						}
					}
					for (int i = 0; i < size1; i++) {
						for (int j = 0; j < size1; j++) {
							a3[i][j] = arr3[i][j];
						}
					}
					for (int i = 0; i < sss; i++) {
						for (int j = 0; j < sss; j++) {
							a3[i][size1] = a3[i][s5];			//size1 == sss - 1
							a3[size1][j] = a3[s5][j];
						}
					}
					a3[s5][size1] = 1;
					a3[size1][s5] = 1;

					printf("Расщепление вершины %d для графа M1:\n", s5);
					print_matrix(sss, a3);
					for (int i = 0; i < sss; ++i) free(a3[i]);
					free(a3);
				}
				else if (k == 27) {
					break;
				}
			}

			for (int i = 0; i < size1; ++i) free(arr1[i]);
			free(arr1);
			for (int i = 0; i < size1; ++i) free(arr2[i]);
			free(arr2);
			for (int i = 0; i < size1; ++i) free(arr3[i]);
			free(arr3);

			_getch();
			break;
		}

		switch (menu) {			//5
		case '5':
			system("cls");

			int N;
			printf("Введите количество вершин графа: ");
			scanf("%d", &N);
			int** A = (int**)malloc(sizeof(int*) * N);
			for (int i = 0; i < N; i++) A[i] = (int*)malloc(sizeof(int) * N);

			create_matrix(N, A);
			printf("Матрица смежности:\n");
			print_matrix(N, A);

			G = make_G(N, A);
			printf("Списки смежности:\n");
			print_G(N, G);

			while (1) {
				printf("Введите Q(q), W(w), E(e), D(d) для отождествления, стягивания ребра,расщепления и удаления ребра\n");
				printf("U(u), I(i) для объединения и пересечения графов, C(c) для соединения графов или ESC для выхода:\n");
				int k = _getch();
				if ((k == 'Q') || (k == 'q')) {
					//Отождествление
					merge_G(N, G, 0);
					print_G(N, G);
				}
				else if ((k == 'W') || (k == 'w')) {
					//Стягивание
					merge_G(N, G, 1);
					print_G(N, G);
				}
				else if ((k == 'E') || (k == 'e')) {
					//Расщепление
					split_G(N, G);
					print_G(N, G);
				}
				else if ((k == 'U') || (k == 'u')) {
					// Объединение графов
					int N2;
					printf("Введите количество вершин второго графа: ");
					scanf("%d", &N2);

					int** B = (int**)malloc(sizeof(int*) * N2);
					for (int i = 0; i < N2; i++) {
						B[i] = (int*)malloc(sizeof(int) * N2);
					}

					create_matrix(N2, B);
					printf("Матрица смежности второго графа:\n");
					print_matrix(N2, B);
					Node* G2 = make_G(N2, B);
					Node* unionG = union_Graphs(N, G, N2, G2);

					printf("Объединенный граф:\n");
					print_G(N + N2, unionG);

					free_Graph(N2, G2);
					free_Graph(N + N2, unionG);

					for (int i = 0; i < N2; i++) {
						free(B[i]);
					}
					free(B);
				}
				else if ((k == 'I') || (k == 'i')) {
					// Пересечение графов
					int N2;
					printf("Введите количество вершин второго графа: ");
					scanf("%d", &N2);

					int** B = (int**)malloc(sizeof(int*) * N2);
					for (int i = 0; i < N2; i++) {
						B[i] = (int*)malloc(sizeof(int) * N2);
					}

					create_matrix(N2, B);
					printf("Матрица смежности второго графа:\n");
					print_matrix(N2, B);

					Node* G2 = make_G(N2, B);
					Node* intersectG = intersect_Graphs(N, G, G2);

					printf("Граф пересечения:\n");
					print_G(N, intersectG);

					free_Graph(N2, G2);
					free_Graph(N, intersectG);

					for (int i = 0; i < N2; i++) {
						free(B[i]);
					}
					free(B);
				}
				else if ((k == 'C') || (k == 'c')) {
					// Соединение графов
					int N2;
					printf("Введите количество вершин второго графа: ");
					scanf("%d", &N2);

					int** B = (int**)malloc(sizeof(int*) * N2);
					for (int i = 0; i < N2; i++) {
						B[i] = (int*)malloc(sizeof(int) * N2);
					}

					create_matrix(N2, B);
					printf("Матрица смежности второго графа:\n");
					print_matrix(N2, B);

					Node* G2 = make_G(N2, B);
					Node* joinedG = join_Graphs(N, G, N2, G2);

					printf("Соединенный граф:\n");
					print_G(N + N2, joinedG);

					free_Graph(N2, G2);
					free_Graph(N + N2, joinedG);

					for (int i = 0; i < N2; i++) {
						free(B[i]);
					}
					free(B);
				}
				else if ((k == 'D') || (k == 'd')) {
					int u, v;
					printf("Введите вершины u и v для удаления ребра: ");
					scanf("%d %d", &u, &v);

					// Удаление ребра в существующем графе (G нужно определить в вашей программе)
					remove_edge(G, u, v);
					printf("Ребро между %d и %d удалено.\n", u, v);
					print_G(N, G);
					break;
				}
				// esc
				else if (k == 27) {
					break;
				}
			}

			for (int i = 0; i < N; i++) {
				free(A[i]);
			}
			free(A);
			free_Graph(N, G);

			_getch();
			break;
		}

		switch (menu) {			//3
		case '3':
			system("cls");

			int size1, size2;
			int** arr1;
			int** arr2;
			int** arr3;

			printf("Введите размер матрицы смежности M1: ");
			scanf("%d", &size1);
			printf("\n");

			printf("Введите размер матрицы смежности M2: ");
			scanf("%d", &size2);
			printf("\n");

			arr1 = (int**)malloc(sizeof(int*) * size1);
			for (int i = 0; i < size1; i++) {
				arr1[i] = (int*)malloc(sizeof(int) * size1);
			}

			arr2 = (int**)malloc(sizeof(int*) * size2);
			for (int i = 0; i < size2; i++) {
				arr2[i] = (int*)malloc(sizeof(int) * size2);
			}

			printf("Матрица M1:\n");
			create_matrix(size1, arr1);
			print_matrix(size1, arr1);

			printf("Матрица M2:\n");
			create_matrix(size2, arr2);
			print_matrix(size2, arr2);

			printf("===================================================================\n");

			printf("Объединение графов M1 и M2:\n\n");

			if (size1 < size2) {
				arr3 = (int**)malloc(sizeof(int*) * size2);
				for (int i = 0; i < size2; i++) {
					arr3[i] = (int*)malloc(sizeof(int) * size2);
				}
				obed(arr1, arr2, arr3, size1, size2);
			}

			else {
				arr3 = (int**)malloc(sizeof(int*) * size1);
				for (int i = 0; i < size1; i++) {
					arr3[i] = (int*)malloc(sizeof(int) * size1);
				}

				obed(arr2, arr1, arr3, size2, size1);
			}

			printf("===================================================================\n");

			printf("Пересечение графов M1 и M2:\n\n");

			if (size1 < size2) {
				arr3 = (int**)malloc(sizeof(int*) * size1);
				for (int i = 0; i < size1; i++) {
					arr3[i] = (int*)malloc(sizeof(int) * size1);
				}
				peres(arr1, arr2, arr3, size1, size2);
			}

			else {
				arr3 = (int**)malloc(sizeof(int*) * size2);
				for (int i = 0; i < size2; i++) {
					arr3[i] = (int*)malloc(sizeof(int) * size2);
				}

				peres(arr2, arr1, arr3, size2, size1);
			}

			printf("===================================================================\n");

			printf("Кольцевая сумма графов M1 и M2:\n\n");

			if (size1 < size2) {
				arr3 = (int**)malloc(sizeof(int*) * size2);
				for (int i = 0; i < size2; i++) {
					arr3[i] = (int*)malloc(sizeof(int) * size2);
				}
				kol_sum(arr1, arr2, arr3, size1, size2);
			}
			else {
				arr3 = (int**)malloc(sizeof(int*) * size1);
				for (int i = 0; i < size1; i++) {
					arr3[i] = (int*)malloc(sizeof(int) * size1);
				}

				kol_sum(arr2, arr1, arr3, size2, size1);
			}

			for (int i = 0; i < size1; ++i) free(arr1[i]);
			free(arr1);
			for (int i = 0; i < size2; ++i) free(arr2[i]);
			free(arr2);

			_getch();
			break;
		}

		switch (menu) {			//4
		case '4':
			system("cls");

			int size1, size2;
			int** arr1;
			int** arr2;
			int** arr3;

			printf("Введите размер матрицы смежности M1: ");
			scanf("%d", &size1);
			printf("\n");

			printf("Введите размер матрицы смежности M2: ");
			scanf("%d", &size2);
			printf("\n");

			arr1 = (int**)malloc(sizeof(int*) * size1);
			for (int i = 0; i < size1; i++) {
				arr1[i] = (int*)malloc(sizeof(int) * size1);
			}

			arr2 = (int**)malloc(sizeof(int*) * size2);
			for (int i = 0; i < size2; i++) {
				arr2[i] = (int*)malloc(sizeof(int) * size2);
			}

			printf("Матрица M1:\n");
			create_matrix(size1, arr1);
			print_matrix(size1, arr1);

			printf("Матрица M2:\n");
			create_matrix(size2, arr2);
			print_matrix(size2, arr2);

			printf("===================================================================\n");

			printf("Декартово произведение графов M1 и M2:\n\n");

			int size3 = size1 * size2;
			arr3 = (int**)malloc(sizeof(int*) * size3);
			for (int i = 0; i < size3; i++) {
				arr3[i] = (int*)malloc(sizeof(int) * size3);
			}

			int m = -1, n = -1;

			for (int i = 0; i < size1; i++) {
				for (int k = 0; k < size2; k++) {
					n++; //увеличивается каждый раз,когда меняется вершина второго графа
					m = -1; //сбрасывается в -1 на каждый новый шаг по вершинам M2,чтобы перезапустить индекс для первой графа.
					for (int j = 0; j < size1; j++) {
						for (int l = 0; l < size2; l++) {
							m++;
							if (i == j) { //для текущей вершины из M1 есть связь в графе M2 между k и l
								arr3[m][n] = arr2[k][l]; //устанавливаем связь,копирование значений из матрицы M2
							}
							if (k == l) { //для текущей вершины из M2 есть связь в графе M1 между i и j
								arr3[m][n] = arr1[i][j];
							}
							if ((i != j) && (k != l)) { // связи нет
								arr3[m][n] = 0;
							}
						}
					}
				}
			}
			//вершины будут представлены как пары (i, k), где i — вершина из первого графа M1, а k — вершина из второго графа M2.
			//ребра будут присутствовать в зависимости от связей в графах M1 и M2.

			printf("Результат \n");
			print_matrix(size3, arr3);

			for (int i = 0; i < size1; ++i) free(arr1[i]);
			free(arr1);
			for (int i = 0; i < size2; ++i) free(arr2[i]);
			free(arr2);
			for (int i = 0; i < size3; ++i) free(arr3[i]);
			free(arr3);

			_getch();
			break;
		}

	} while (menu != 27);
	return 0;
}