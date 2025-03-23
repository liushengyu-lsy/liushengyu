#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "stack.h"
#include "calculator.h"

// 判断运算符优先级
int precedence(char op) {
    if (op == '+' || op == '-')
        return 1;
    if (op == '*' || op == '/')
        return 2;
    return 0;
}

// 计算操作
double applyOp(double a, double b, char op) {
    switch (op) {
        case '+': return a + b;
        case '-': return a - b;
        case '*': return a * b;
        case '/':
            if (b == 0) {
                printf("错误：除数不能为零！\n");
                exit(1);
            }
            return a / b;
    }
    return 0;
}

// 计算表达式的值
double evaluate(char *expression) {
    LinkStack values, ops;
    initStack(&values);
    initStack(&ops);
int i;
    for ( i = 0; expression[i] != '\0'; i++) {
        if (expression[i] == ' ')
            continue;

        if (isdigit(expression[i])) {
            double val = 0;
            while (i < strlen(expression) && isdigit(expression[i])) {
                val = (val * 10) + (expression[i] - '0');
                i++;
            }
            i--;
            push(&values, val);
        } else if (expression[i] == '(') {
            push(&ops, expression[i]);
        } else if (expression[i] == ')') {
            while (!isEmpty(&ops) && getTop(&ops) != '(') {
                double val2 = pop(&values);
                double val1 = pop(&values);
                char op = pop(&ops);
                push(&values, applyOp(val1, val2, op));
            }
            if (!isEmpty(&ops) && getTop(&ops) == '(')
                pop(&ops);
        } else {
            while (!isEmpty(&ops) && precedence(getTop(&ops)) >= precedence(expression[i])) {
                double val2 = pop(&values);
                double val1 = pop(&values);
                char op = pop(&ops);
                push(&values, applyOp(val1, val2, op));
            }
            push(&ops, expression[i]);
        }
    }

    while (!isEmpty(&ops)) {
        double val2 = pop(&values);
        double val1 = pop(&values);
        char op = pop(&ops);
        push(&values, applyOp(val1, val2, op));
    }

    return pop(&values);
}    
