#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Node {
    char name[20];
    struct Node* next,*prev;
} Node;

typedef struct Queue {
    Node* head;
    Node* tail;
} Queue;

void addElem(Queue* q, char *name) {
    Node* tmp = (Node*)malloc(sizeof(Node));
    strcpy(tmp->name,name);
    tmp->next = NULL;
    tmp->prev = NULL;
    if (q->tail == NULL) {

        q->head = tmp;
        q->tail = tmp;
    }
    else {
        q->tail->next = tmp;
        tmp->prev = q->tail;
        q->tail = tmp;
    }
}

void deleteElem(Queue* q) {
    if (q->head == NULL) {
        printf("Queue is empty!\n");
        exit(1);
    }
    q->head = q->head->next;
    if (q->head == NULL) {
        q->tail = NULL;
    }

}

void displayElems(Queue *q) {
    Node* tmp = q->head;
    while (tmp) {
        printf("%s  ", tmp->name);
        tmp = tmp->next;
    }
}

void displayMenu(Queue* q) {
    char name[20];
    printf("----------Menu----------\n");
    printf("1. Add name\n");
    printf("2. Display names\n");
    printf("3. Delete name\n");
    int choice;
    scanf("%d", &choice);
    system("cls");
    switch (choice) {
    case(1):
        int count;
        printf("How many names do u want to add? ");
        scanf("%d", &count);

        for (int i = 0; i < count; i++) {
            printf("Write down %d name: ", i + 1);
            scanf("%s", name);
            addElem(q,name);
        }
        printf("\nTap some button to return to main menu...");
        getchar();
        system("cls");
        displayMenu(q);
        break;
    case(2):
        displayElems(q);
        printf("\nTap some button to return to main menu...");
        getchar();
        getchar();
        system("cls");
        displayMenu(q);
        break;
    case(3):
        deleteElem(q);
        printf("\nThe element deleted.\nTap some button to return to main menu...");
        getchar();
        system("cls");
        displayMenu(q);
        break;
    }
}

int main() { 
    Queue* q = (Queue*)malloc(sizeof(Queue));
    q->head = NULL;
    q->tail = NULL;
    displayMenu(q);
    
    return 0;
}
