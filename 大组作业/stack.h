#ifndef STACK_H
#define STACK_H

// ������ջ�ڵ�ṹ
typedef struct StackNode {
    double data;
    struct StackNode *next;
} StackNode;

// ������ջ�ṹ
typedef struct {
    StackNode *top;
} LinkStack;

// ��ʼ����ջ
void initStack(LinkStack *s);

// �ж���ջ�Ƿ�Ϊ��
int isEmpty(LinkStack *s);

// ��ջ����
void push(LinkStack *s, double value);

// ��ջ����
double pop(LinkStack *s);

// ��ȡջ��Ԫ��
double getTop(LinkStack *s);

#endif    
