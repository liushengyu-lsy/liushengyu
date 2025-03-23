#include <stdio.h>
#include <stdlib.h>
#include "stack.h"

// 初始化链栈
void initStack(LinkStack *s) {
    s->top = NULL;
}

// 判断链栈是否为空
int isEmpty(LinkStack *s) {
    return s->top == NULL;
}

// 入栈操作
void push(LinkStack *s, double value) {
    StackNode *newNode = (StackNode *)malloc(sizeof(StackNode));
    newNode->data = value;
    newNode->next = s->top;
    s->top = newNode;
}

// 出栈操作
double pop(LinkStack *s) {
    if (isEmpty(s)) {
        printf("栈为空，无法出栈！\n");
        return 0;
    }
    double value = s->top->data;
    StackNode *temp = s->top;
    s->top = s->top->next;
    free(temp);
    return value;
}

// 获取栈顶元素
double getTop(LinkStack *s) {
    if (isEmpty(s)) {
        printf("栈为空，无法获取栈顶元素！\n");
        return 0;
    }
    return s->top->data;
}    
