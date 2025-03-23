#ifndef CALCULATOR_H
#define CALCULATOR_H

#include "stack.h"

// 判断运算符优先级
int precedence(char op);

// 计算操作
double applyOp(double a, double b, char op);

// 计算表达式的值
double evaluate(char *expression);

#endif    
