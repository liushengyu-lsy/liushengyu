#include <stdio.h>
#include "calculator.h"

int main() {
    char expression[100];
    printf("����������������ʽ��");
    fgets(expression, sizeof(expression), stdin);
    expression[strcspn(expression, "\n")] = 0;  // ȥ�����з�

    double result = evaluate(expression);
    printf("���ʽ��ֵΪ��%.2f\n", result);

    return 0;
}    
