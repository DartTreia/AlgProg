#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int max = 0;
int k = 0;//кол-во повторов

typedef struct Tree {
    int data;
    struct Tree* left;
    struct Tree* right;
} Tree;

void findMaxLvl(Tree* root,int count) {
    if (root == NULL) {
        return ;
    }
    if (root->right != NULL || root->left != NULL) {
        count++;
    }
    else {
        if (count > max)max = count;
        count = 0;
    }
    findMaxLvl(root->right, count);
    findMaxLvl(root->left, count);
}

void findMaxRepeatElemLvl(Tree* root, int data,int count) {
    if (root == NULL) {
        return;
    }
    if (k>0 && root->data == data) {
        if (count > max)max = count;
        k--;
        if (k == 0) {
            printf("The max lvl of this repeated element is %d\n", max);
        }
    }
    if (root->right != NULL || root->left != NULL) {
        count++;
    }
    else { count = 0; }
   
    findMaxRepeatElemLvl(root->right, data, count);
    findMaxRepeatElemLvl(root->left, data, count);
}

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
int find(Tree* root, int data,int count) {
    if (root == NULL) {
        return count;
    }
    if (root->data == data)count++;
    count =find(root->right, data,count);
    count =find(root->left, data, count);
    return count;
}
int calcDiff(Tree* root, int data, int count) {
    if (root == NULL) {
        return count;
    }
    count++;
    if (root->data == data) printf("The difficult of find function for the value %d is O(%d)..\n", data, count);
    count = calcDiff(root->right, data, count);
    count = calcDiff(root->left, data, count);
    return count;
}
Tree* addSpecialElem(Tree* root, int data) {
    if (find(root, data, 0) != 0) {
        printf("This element already exists, choose another\n");
        return root;
    }
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
void displayMenu(Tree* root) {
    int data = 0;
    int count;
    printf("----------Menu----------\n");
    printf("1. Add elements\n");
    printf("2. Add special elements\n");
    printf("3. Display elements\n");
    printf("4. Find elements\n");
    printf("5. Calculate difficult of find function by value\n");
    printf("6. Max lvl of the tree\n");
    printf("7. Max lvl of the repeated element\n");
    int choice;
    scanf("%d", &choice);
    system("cls");
    switch (choice) {
    case(1):
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
        getchar();
        system("cls");
        displayMenu(root);
        break;
    case(2):
        printf("How many special elements do u want to add? ");
        scanf("%d", &count);

        for (int i = 0; i < count; i++) {
            printf("Write down %d element: ", i + 1);
            scanf("%d", &data);
            if (find(root, data, 0) != 0) i--;
            if (root == NULL)
                root = addSpecialElem(root, data);
            else addSpecialElem(root, data);
        }
        printf("\nTap some button to return to main menu...");
        getchar();
        getchar();
        system("cls");
        displayMenu(root);
        break;
    case(3):
        printTree(root,0);
        printf("\nTap some button to return to main menu...");
        getchar();
        getchar();
        system("cls");
        displayMenu(root);
        break;
    case(4):
        printf("What element do u want to find? ");
        scanf("%d", &data);
        count = find(root, data, 0);
        printf("There are only %d elements in the tree..\n", count);
        printf("Tap some button to return to main menu...");
        getchar();
        getchar();
        system("cls");
        displayMenu(root);
        break;
    case(5):
        printf("What element do u want to find? ");
        scanf("%d", &data);
        count = calcDiff(root, data, 0);
        printf("Tap some button to return to main menu...");
        getchar();
        getchar();
        system("cls");
        displayMenu(root);
        break;
    case(6):
        findMaxLvl(root, 0);
        printf("The max lvl of the tree is %d\n", max);
        printf("Tap some button to return to main menu...");
        getchar();
        getchar();
        system("cls");
        max = 0;
        displayMenu(root);
        break;
    case(7):
        printf("What element do u want to find? ");
        scanf("%d", &data);
        k = find(root, data, 0);
        findMaxRepeatElemLvl(root, data,0);
        printf("Tap some button to return to main menu...");
        getchar();
        getchar();
        system("cls");
        max = 0;
        k = 0;
        displayMenu(root);
        break;
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