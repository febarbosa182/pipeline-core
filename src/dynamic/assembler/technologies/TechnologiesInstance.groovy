package br.com.dynamic.assembler.technologies

import br.com.dynamic.assembler.technologies.Angular
import br.com.dynamic.assembler.technologies.DotnetCore
import br.com.dynamic.assembler.technologies.Node
import br.com.dynamic.assembler.technologies.TechnologiesInterface

enum TechnologiesInstance{
    ANGULAR(Angular.class),
    NODE(Node.class),

    Class<TechnologiesInterface> technologiesInstance;

    TechnologiesInstance(Class<TechnologiesInterface> technologiesInstance){
        this.technologiesInstance=technologiesInstance
    }

    Class<TechnologiesInterface> getTechnologiesInstance(){
        return technologiesInstance.newInstance()
    }

}
