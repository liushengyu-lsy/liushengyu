#include <stdio.h>
#include <stdlib.h>
#include "stack.h"

// ��ʼ����ջ
void initStack(LinkStack *s) {
    s->top = NULL;
}

// �ж���ջ�Ƿ�Ϊ��
int isEmpty(LinkStack *s) {
    return s->top == NULL;
}

// ��ջ����
void push(LinkStack *s, double value) {
    StackNode *newNode = (StackNode *)malloc(sizeof(StackNode));
    newNode->data = value;
    newNode->next = s->top;
    s->top = newNode;
}

// ��ջ����
double pop(LinkStack *s) {
    if (isEmpty(s)) {
        printf("ջΪ�գ��޷���ջ��\n");
        return 0;
    }
    double value = s->top->data;
    StackNode *temp = s->top;
    s->top = s->top->next;
    free(temp);
    return value;
}

// ��ȡջ��Ԫ��
double getTop(LinkStack *s) {
    if (isEmpty(s)) {
        printf("ջΪ�գ��޷���ȡջ��Ԫ�أ�\n");
        return 0;
    }
    return s->top->data;
}    
