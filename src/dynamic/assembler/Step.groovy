package br.com.dynamic.assembler

class Step{
    def stepObject;
    def stepName;

    Step(stepObject, stepName){
        this.stepObject = stepObject;
        this.stepName = stepName;
    }

    def execStep(context){
        stepObject.call(context)
    }

    def getName(){
        return stepName
    }
}
