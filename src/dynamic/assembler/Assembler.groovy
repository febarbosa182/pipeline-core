package dynamic.assembler

import dynamic.assembler.Step
import dynamic.assembler.technologies.TechnologiesInstance
import dynamic.assembler.technologies.TechnologiesInterface

class Assembler{
    TechnologiesInterface technology

    def compose(jenkins) {
        jenkins.echo "Pipeline Assemble!"

        technology = TechnologiesInstance
                .valueOf(jenkins.env.TECH_TYPE.toUpperCase())
                    .getTechnologiesInstance()

        technology.fillSteps(jenkins)

    }

    def runSteps(jenkins) {
        technology.stepList.each{
            jenkins.stage(it.getName()){
                it.execStep(jenkins)
            }
        }
    }
}
