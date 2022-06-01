package br.com.dynamic.assembler.technologies

import br.com.dynamic.assembler.Step
import br.com.dynamic.assembler.StepModel
import java.util.regex.Matcher

class Angular extends Common implements TechnologiesInterface, Serializable{
    def stepList = [];

    final StepModel[] ciSteps = [
        [
            identifier: 'checkout',
            stepName: 'Checkout',
            urlRepo: 'https://github.com/febarbosa182/pipestep-checkout.git',
            branch: 'v1.0.0',
            instanceClass: 'br.com.dynamic.checkout.Checkout'
        ],
        [
            identifier: 'dependencies',
            stepName: 'Dependencies',
            urlRepo: 'https://github.com/febarbosa182/pipestep-dependencies-angular.git',
            branch: 'v1.0.0',
            instanceClass: 'br.com.dynamic.dependencies.Dependencies'
        ],
        [
            identifier: 'build',
            stepName: 'Build',
            urlRepo: 'https://github.com/febarbosa182/pipestep-build-angular.git',
            branch: 'v1.0.0',
            instanceClass: 'br.com.dynamic.build.Build'
        ],
        [
            identifier: 'buildpublish',
            stepName: 'Docker Build and Publish',
            urlRepo: 'https://github.com/febarbosa182/pipestep-docker-build-and-publish.git',
            branch: 'v1.0.0',
            instanceClass: 'br.com.dynamic.docker.BuildAndPublish'
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

    // CD STEPS FOR BRANCH MASTER/MAIN
    final StepModel[] cdMaster = [
        [
            identifier: 'deploy',
            stepName: 'Deploy Helm Chart',
            urlRepo: 'https://github.com/febarbosa182/pipestep-helm-deploy.git',
            branch: 'v1.0.0',
            instanceClass: 'br.com.dynamic.deploy.Deploy'
        ]
    ]

    def fillSteps (jenkins) {

        StepModel[] currentPipe

        switch( jenkins.env.REPO_BRANCH ) {
            case ~/^develop/:
                currentPipe = ciSteps.plus(cdDevelop)
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
            case ~/^main/:
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