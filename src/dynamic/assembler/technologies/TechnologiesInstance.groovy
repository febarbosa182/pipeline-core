package dynamic.assembler.technologies

import dynamic.assembler.technologies.Angular
import dynamic.assembler.technologies.Node
import dynamic.assembler.technologies.TechnologiesInterface

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
