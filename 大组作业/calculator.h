#ifndef CALCULATOR_H
#define CALCULATOR_H

#include "stack.h"

// �ж���������ȼ�
int precedence(char op);

// �������
double applyOp(double a, double b, char op);

// ������ʽ��ֵ
double evaluate(char *expression);

#endif    
