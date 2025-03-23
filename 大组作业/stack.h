#ifndef STACK_H
#define STACK_H

// 定义链栈节点结构
typedef struct StackNode {
    double data;
    struct StackNode *next;
} StackNode;

// 定义链栈结构
typedef struct {
    StackNode *top;
} LinkStack;

// 初始化链栈
void initStack(LinkStack *s);

// 判断链栈是否为空
int isEmpty(LinkStack *s);

// 入栈操作
void push(LinkStack *s, double value);

// 出栈操作
double pop(LinkStack *s);

// 获取栈顶元素
double getTop(LinkStack *s);

#endif    
