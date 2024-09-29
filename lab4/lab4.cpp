#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

typedef struct Tree {
    int data;
    struct Tree* left;
    struct Tree* right;
} Tree;

Tree* addElem(Tree* root, int data) {
    Tree* tmp = NULL;
    if (!(tmp = (Tree*)malloc(sizeof(sizeof(Tree))))) {
        printf("Memory error");
        exit(1);
    }
    tmp->data = data;
    tmp->left = NULL;
    tmp->right = NULL;
    if (root == NULL) {
        root = tmp;
        return root;
    }
    if (data < root->data) {
        root->left = addElem(root->left, data);
    }
    else {
        root->right = addElem(root->right, data);
    }
    return root;
}

void printTree(Tree* root,int size) {
    if (root == NULL) {
        return;
    }

    printTree(root->right, size + 1);
    printf("\n");
    for (int i = 0; i < size; i++) {
        printf("   "); 
    }
    printf("%d\n", root->data);

    printTree(root->left, size + 1);
}

void displayMenu(Tree* root) {
    int data = 0;;
    printf("----------Menu----------\n");
    printf("1. Add element\n");
    printf("2. Display elements\n");
    printf("3. Delete name\n");
    int choice;
    scanf("%d", &choice);
    system("cls");
    switch (choice) {
    case(1):
        int count;
        printf("How many elements do u want to add? ");
        scanf("%d", &count);

        for (int i = 0; i < count; i++) {
            printf("Write down %d element: ", i + 1);
            scanf("%d", &data);
            if (root == NULL)
                root = addElem(root, data);
            else addElem(root, data);
        }
        printf("\nTap some button to return to main menu...");
        getchar();
        system("cls");
        displayMenu(root);
        break;
    case(2):
        printTree(root,0);
        printf("\nTap some button to return to main menu...");
        getchar();
        getchar();
        system("cls");
        displayMenu(root);
        break;
   /* case(3):
        deleteElem(q);
        printf("\nThe element deleted.\nTap some button to return to main menu...");
        getchar();
        system("cls");
        displayMenu(q);
        break;*/
    default:
        printf("Wrong button!");
        displayMenu(root);
        break;
    }
}

void main() {
    Tree* root = NULL;

    displayMenu(root);

}