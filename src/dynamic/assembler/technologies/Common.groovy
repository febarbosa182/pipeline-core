package br.com.dynamic.assembler.technologies

import br.com.dynamic.assembler.Step

class Common {
  def loadLibrary(context, String identifier, String urlRepo, String branch){
    def library = context.library(identifier: "${identifier}@${branch}", 
      retriever: context.modernSCM(
        [
          $class: 'GitSCMSource',
          remote: "${urlRepo}", 
          credentialsId: 'jenkins.app'
        ])
    )

    return library
  }

  Step stepInstance(context, String identifier, String urlRepo, String branch, String stepName, String instanceClass){
        def library = loadLibrary(context, identifier, urlRepo, branch)
        def libraryInstance = library.loadClass(instanceClass).newInstance()
        return new Step(libraryInstance, stepName)
  }
}