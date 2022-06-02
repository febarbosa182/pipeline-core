package dynamic.assembler.technologies

import dynamic.assembler.Step
import dynamic.assembler.StepModel
import java.util.regex.Matcher

class Angular extends Common implements TechnologiesInterface, Serializable{
    def stepList = [];

    final StepModel[] ciSteps = [
        [
            identifier: 'checkout',
            stepName: 'Checkout',
            urlRepo: 'https://github.com/febarbosa182/pipestep-checkout.git',
            branch: 'v1.1.0',
            instanceClass: 'dynamic.checkout.Checkout'
        ],
        [
            identifier: 'dependencies',
            stepName: 'Dependencies',
            urlRepo: 'https://github.com/febarbosa182/pipestep-dependencies-angular.git',
            branch: 'v1.1.0',
            instanceClass: 'dynamic.dependencies.Dependencies'
        ],
        [
            identifier: 'build',
            stepName: 'Build',
            urlRepo: 'https://github.com/febarbosa182/pipestep-build-angular.git',
            branch: 'v1.1.0',
            instanceClass: 'dynamic.build.Build'
        ],
        [
            identifier: 'buildpublish',
            stepName: 'Docker Build and Publish',
            urlRepo: 'https://github.com/febarbosa182/pipestep-docker-build-and-publish.git',
            branch: 'v1.1.0',
            instanceClass: 'dynamic.docker.BuildAndPublish'
        ]
    ]

    // CD STEPS FOR BRANCH DEVELOP
    final StepModel[] cdDevelop = []
    
    // CD STEPS FOR BRANCH RELEASE
    final StepModel[] cdRelease = []

    // CD STEPS FOR BRANCH MASTER/MAIN
    final StepModel[] cdMain = [
        [
            identifier: 'deploy',
            stepName: 'Deploy Helm Chart',
            urlRepo: 'https://github.com/febarbosa182/pipestep-helm-deploy.git',
            branch: 'v1.1.0',
            instanceClass: 'dynamic.deploy.Deploy'
        ]
    ]

    def fillSteps (jenkins) {

        StepModel[] currentPipe

        switch( jenkins.env.REPO_BRANCH ) {
            case ~/^develop/:
                currentPipe = ciSteps.plus(cdDevelop)
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