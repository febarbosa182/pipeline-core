package dynamic.assembler.technologies

import dynamic.assembler.Step
import dynamic.assembler.StepModel
import java.util.regex.Matcher

class Node extends Common implements TechnologiesInterface, Serializable{
    def stepList = [];

    final StepModel[] ciSteps = [
        [
            identifier: 'checkout',
            stepName: 'Checkout',
            urlRepo: 'https://github.com/febarbosa182/pipestep-checkout.git',
            branch: 'v1.1.0',
            instanceClass: 'dynamic.checkout.Checkout'
        ]
    ]

    // CD STEPS FOR BRANCH DEVELOP
    final StepModel[] cdDevelop = []
    
    // CD STEPS FOR BRANCH RELEASE
    final StepModel[] cdRelease = []

    // CD STEPS FOR BRANCH MAIN
    final StepModel[] cdMain = []

    def fillSteps (jenkins) {

        StepModel[] currentPipe

        switch( jenkins.env.REPO_BRANCH ) {
            case ~/^develop/:
                currentPipe = ciSteps + cdDevelop
                break
            case ~/^release/:
                currentPipe = ciSteps.plus(cdRelease)
                break
            case ~/^main/:
                currentPipe = ciSteps.plus(cdMain)
                break
            default:
                currentPipe = ciSteps
                break
        }

        currentPipe.each{
            this.stepList.add(
                stepInstance(jenkins, it.identifier, it.urlRepo, it.branch, it.stepName, it.instanceClass)
            )
            jenkins.echo "${it.stepName} Loaded"
        }
    }

}