#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int* createArr(int* arr, int len) {
    arr = (int*)malloc(len * sizeof(int*));
    return arr;
}
int compare(const void* a, const void* b)
{
    return (*(int*)a - *(int*)b);
}
int* randFill(int* arr, int len) {
    for (int o = 0; o < len; o++) {
        arr[o] = rand() % 100 - 50;
    }
    return arr;
}
int* incFill(int* arr, int len) {
    for (int o = 0; o < len; o++) {
        arr[o] = o;
    }
    return arr;
}
int* decFill(int* arr, int len) {
    for (int o = 0; o < len; o++) {
        arr[o] = len/(o+1);
    }
    return arr;
}
int* incAndDecFill(int* arr, int len) {
    for (int o = 0; o < len/2; o++) {
        arr[o] = o;
        arr[o+len/2] = len / (o + 1);
    }
    return arr;
}
void shell(int* items, int count)
{
    int i, j, gap, k;
    int x, a[5];

    a[0] = 9; a[1] = 5; a[2] = 3; a[3] = 2; a[4] = 1;

    for (k = 0; k < 5; k++) {
        gap = a[k];
        for (i = gap; i < count; ++i) {
            x = items[i];
            for (j = i - gap; (x < items[j]) && (j >= 0); j = j - gap)
                items[j + gap] = items[j];
            items[j + gap] = x;
        }
    }
}

void qs(int* items, int left, int right) //вызов функции: qs(items, 0, count-1);
{
    int i, j;
    int x, y;



    i = left; j = right;

    /* выбор компаранда */
    x = items[(left + right) / 2];

    do {
        while ((items[i] < x) && (i < right)) i++;
        while ((x < items[j]) && (j > left)) j--;

        if (i <= j) {
            y = items[i];
            items[i] = items[j];
            items[j] = y;
            i++; j--;
        }
    } while (i <= j);

    if (left < j) qs(items, left, j);
    if (i < right) qs(items, i, right);
}


void main() {
    setvbuf(stdin, NULL, _IONBF, 0);
    setvbuf(stdout, NULL, _IONBF, 0);
    clock_t start, end;
    srand(time(NULL));
    int size = 400000;
    int* array = { 0 };
    printf("||||RANDOM fill array||||\n");
    array = createArr(array, size);
    array = randFill(array, size);

    start = clock();
    shell(array, size);
    end = clock();
    printf("Shell sort: %.3f sec\n", ((double)(end - start))/CLOCKS_PER_SEC);

    array = randFill(array, size);
    start = clock();
    qs(array, 0,size-1);
    end = clock();
    printf("Fast sort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    array = randFill(array, size);
    start = clock();
    qsort(array,size,sizeof(int *),compare);
    end = clock();
    printf("Qsort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    printf("\n||||INCREASE fill array||||\n");
    array = incFill(array, size);

    start = clock();
    shell(array, size);
    end = clock();
    printf("Shell sort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    array = incFill(array, size);
    start = clock();
    qs(array, 0, size - 1);
    end = clock();
    printf("Fast sort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    array = incFill(array, size);
    start = clock();
    qsort(array, size, sizeof(int*), compare);
    end = clock();
    printf("Qsort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    printf("\n||||DECREASE fill array||||\n");
    array = decFill(array, size);

    start = clock();
    shell(array, size);
    end = clock();
    printf("Shell sort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    array = decFill(array, size);
    start = clock();
    qs(array, 0, size - 1);
    end = clock();
    printf("Fast sort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    array = decFill(array, size);
    start = clock();
    qsort(array, size, sizeof(int*), compare);
    end = clock();
    printf("Qsort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    printf("\n||||INCREASE then DECREASE fill array||||\n");
    array = incAndDecFill(array, size);

    start = clock();
    shell(array, size);
    end = clock();
    printf("Shell sort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    array = incAndDecFill(array, size);
    start = clock();
    qs(array, 0, size - 1);
    end = clock();
    printf("Fast sort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    array = incAndDecFill(array, size);
    start = clock();
    qsort(array, size, sizeof(int*), compare);
    end = clock();
    printf("Qsort: %.3f sec\n", ((double)(end - start)) / CLOCKS_PER_SEC);
}

