#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <corecrt_malloc.h>
#include <stdlib.h>
#include <string.h>

struct node {
    char color[10];
    int priority;
    struct node* last, * next;
};

node* find(char* color, node* head) {
    struct node* tmpHead = head;
    struct node* returningList = NULL;
    struct node* tmpRL = NULL;

    while (tmpHead != NULL) {  
        if (strcmp(tmpHead->color, color) == 0) {
            struct node* tmp = (node*)malloc(sizeof(struct node));
            if (tmp == NULL) {
                printf("Ошибка при распределении памяти\n");
                exit(1);
            }
            strcpy(tmp->color, tmpHead->color);
            tmp->priority = tmpHead->priority;
            tmp->next = NULL;  

            if (returningList == NULL) {
                returningList = tmp;  
                tmpRL = returningList; 
            }
            else {
                tmpRL->next = tmp; 
                tmpRL = tmp;       
            }
        }
        tmpHead = tmpHead->next; 
    }
    return returningList;
}
void deleteElem(node* head,node* elems) {
    struct node* tempHead = head;
    while (elems != NULL) {
        while (tempHead != NULL) {
            if (strcmp(tempHead->color, elems->color)==0) {
                if (tempHead == head) {
                    if (tempHead->next != NULL) {
                        head = tempHead->next;
                        head->last = NULL;
                    }
                    else {
                        head = NULL;
                        break;
                    }
                }
                if (tempHead!=NULL && tempHead->last != NULL) {
                    struct node* tmp = tempHead->last;
                    if (tempHead->next != NULL) {
                        tmp->next = tempHead->next;
                        tempHead = tempHead->next;
                        tempHead->last = tmp;
                    }
                        
                    else {
                        tempHead = tempHead->next;
                        tmp->next = NULL;
                    }
                }
                
                
            }
            else {
                tempHead = tempHead->next;
            }
            
        }
        elems = elems->next;
    }
}
void addNewColor(char clr[10], int priority, node** head) {
    struct node* temp = NULL;
    struct node* tmpHead = *head;
    if ((temp = (node*)malloc(sizeof(struct node))) == NULL)
    {
        printf("Ошибка при распределении памяти\n");
        exit(1);
    }
    strcpy(temp->color, clr);
    temp->priority = priority;
    temp->last = NULL;
    temp->next = NULL;
    while (1) {
        if (tmpHead == NULL || tmpHead->priority < temp->priority) {
            temp->next = tmpHead;
            if (tmpHead != NULL && tmpHead->last != NULL) {
                struct node* lastElem = tmpHead->last;
                temp->last = tmpHead->last;
                lastElem->next = temp;  
                tmpHead->last = temp;
            }
            if (tmpHead == *head) {
                if (tmpHead != NULL)
                    tmpHead->last = temp;
                *head = temp;

            }
            break;
        } else if(tmpHead->next!=NULL) tmpHead = tmpHead->next;
        else
        {
            tmpHead->next = temp;
            temp->last = tmpHead;
            break;
        }
        
    }
}

void displayList(node* head) {
    while (head != NULL) {
        printf("Color: %10s    Priority: %d\n", head->color, head->priority);
        head = head->next;
    }
}

void displayMenu(node* head) {
    char color[10];
    int priority; 
    struct node* findedElms;
    printf("----------Menu----------\n");
    printf("1. Add colors\n");
    printf("2. Display colors\n");
    printf("3. Find colors\n");
    printf("4. Delete colors\n");
    int choice;
    scanf("%d", &choice);
    system("cls");
    switch (choice) {
    case(1):
        int count;

        printf("How many colors do u want to add?");
        scanf("%d", &count);

        for (int i = 0; i < count; i++) {
            printf("Write down %d color's name: ", i + 1);
            scanf("%s", color);
            printf("Write down it's priority: ");
            scanf("%d", &priority);
            addNewColor(color, priority, &head);
        }
        printf("\nTap some button to return to main menu...");
        getchar();
        system("cls");
        displayMenu(head);
        break;
    case(2):
        displayList(head);
        printf("\nTap some button to return to main menu...");
        getchar();
        getchar();
        system("cls");
        displayMenu(head);
        break;
    case(3):
        printf("\nWhich color do u want to find? ");
        scanf("%s", color);
        findedElms = find(color, head);
        displayList(findedElms);
        printf("\nTap some button to return to main menu...");
        getchar(); 
        getchar();
        system("cls");
        displayMenu(head);
        break;
    case(4):
        char answer[10];
        displayList(head);
        printf("\nWhich color do u want to delete? ");
        scanf("%s", color);
        findedElms = find(color, head);
        deleteElem(head, findedElms);
        printf("\nTap some button to return to main menu...");
        getchar();
        system("cls");
        displayMenu(head);
        break;
    }
}
void main() {
    struct node* head = NULL;
    displayMenu(head);
}