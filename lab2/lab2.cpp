#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(void)
{
    setvbuf(stdin, NULL, _IONBF, 0);
    setvbuf(stdout, NULL, _IONBF, 0);

    clock_t start, end; // объявляем переменные для определения времени выполнения

    
    int i = 0, j = 0, r, k = 0, size[7] = { 100,200,400,1000,2000,4000,10000 };
    while (k != 7) {
        // Динамическое выделение памяти для массивов
        int** a = (int**)malloc(size[k] * sizeof(int*));
        int** b = (int**)malloc(size[k] * sizeof(int*));
        int** c = (int**)malloc(size[k] * sizeof(int*));
        for (i = 0; i < size[k]; i++) {
            a[i] = (int*)malloc(size[k] * sizeof(int));
            b[i] = (int*)malloc(size[k] * sizeof(int));
            c[i] = (int*)malloc(size[k] * sizeof(int));
        }

        srand(time(NULL)); // инициализируем параметры генератора случайных чисел
        while (i < size[k])
        {
            while (j < size[k])
            {
                a[i][j] = rand() % 100 + 1; // заполняем массив случайными числами
                j++;
            }
            i++;
        }

        srand(time(NULL)); // инициализируем параметры генератора случайных чисел
        while (i < size[k])
        {
            while (j < size[k])
            {
                b[i][j] = rand() % 100 + 1; // заполняем массив случайными числами
                j++;
            }
            i++;
        }
        start = clock();
        for (i = 0; i < size[k]; i++) {
            for (j = 0; j < size[k]; j++) {
                int elem_c = 0;
                for (r = 0; r < size[k]; r++) {
                    elem_c += a[i][r] * b[r][j];
                }
                c[i][j] = elem_c;
            }
        }
        end = clock();
        printf("Array with %d elements completed by %d ticks (%.3f seconds)\n", size[k], end - start, (double)(end - start) / CLOCKS_PER_SEC);

        for (i = 0; i < size[k]; i++) {
            free(a[i]);
            free(b[i]);
            free(c[i]);
        }
        free(a);
        free(b);
        free(c);
        k++;
    }
    return 0;
}