#include <stdio.h>
#include "calculator.h"

int main() {
    char expression[100];
    printf("请输入四则运算表达式：");
    fgets(expression, sizeof(expression), stdin);
    expression[strcspn(expression, "\n")] = 0;  // 去除换行符

    double result = evaluate(expression);
    printf("表达式的值为：%.2f\n", result);

    return 0;
}    
