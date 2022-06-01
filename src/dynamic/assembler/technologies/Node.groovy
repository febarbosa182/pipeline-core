package br.com.dynamic.assembler.technologies

import br.com.dynamic.assembler.Step
import br.com.dynamic.assembler.StepModel
import java.util.regex.Matcher

class Node extends Common implements TechnologiesInterface, Serializable{
    def stepList = [];

    final StepModel[] ciSteps = [
        [
            identifier: 'checkout',
            stepName: 'Checkout',
            urlRepo: 'https://github.com/febarbosa182/pipestep-checkout.git',
            branch: 'main',
            instanceClass: 'br.com.dynamic.checkout.Checkout'
        ]
    ]

    // CD STEPS FOR BRANCH DEVELOP
    final StepModel[] cdDevelop = []
    
    // CD STEPS FOR BRANCH RELEASE
    final StepModel[] cdRelease = []

    // CD STEPS FOR BRANCH STAGING
    final StepModel[] cdStaging = []

    // CD STEPS FOR BRANCH QA
    final StepModel[] cdQa = []

    // CD STEPS FOR BRANCH MASTER
    final StepModel[] cdMaster = []

    def fillSteps (jenkins) {

        StepModel[] currentPipe

        switch( jenkins.env.REPO_BRANCH ) {
            case ~/^develop/:
                currentPipe = ciSteps + cdDevelop
                break
            case ~/^staging/:
                currentPipe = ciSteps.plus(cdStaging)
                break
            case ~/^release/:
                currentPipe = ciSteps.plus(cdRelease)
                break
            case ~/^master/:
                currentPipe = ciSteps.plus(cdMaster)
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