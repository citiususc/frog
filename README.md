# frog
The frog library provides the implementation for the two most used types of fuzzy rule bases:
* Mamdani: the outputs of the rules are fuzzy sets
* TSK: the outputs of the rules are linear combinations of the inputs

The library is focused on real-valued systems, but categorical data, such as classification problems, can be simulated
with natural numbers.

Only one-labeled (i.e. only one fuzzy set) propositions are supported, with two different implementations:
* Linguitisc: a general definition of the fuzzy sets that partitions the variables is used to indicate the label
* Approximative: the definition of the label must be provided specifically for each proposition

## Export/Import
frog uses [SnakeYAML](https://github.com/asomov/snakeyaml) for export/import to/from files using YAML format.

## Quick Example
The following code is part of the tests provided in the library:

```java
// DataBase definition
Variable[] inputs = new Variable[2];
inputs[0] = new Variable("input1", 0, 10);
inputs[0].partition = PartitionBuilder.uniform(inputs[0], 2);
inputs[1] = new Variable("input2", -5, 5);
inputs[1].partition = PartitionBuilder.uniform(inputs[1], 2);
Variable[] outputs = new Variable[1];
outputs[0] = new Variable("output", -50, 50);
DataBase db = new DataBase(inputs, outputs);

// RuleBase definition
RuleBase<TSKRule> rb = new RuleBase<>();
for (int i = 0; i < inputs[0].partition.size(); i++) {
    for (int j = 0; j < inputs[1].partition.size(); j++) {
        List<LinguisticLabelProposition> antecedents = new ArrayList<>();
        antecedents.add(new LinguisticLabelProposition(0, i));
        antecedents.add(new LinguisticLabelProposition(1, j));
        List<double[]> consequents = new ArrayList<>();
        consequents.add(new double[]{i, j, 0.2});
        rb.add(new TSKRule(antecedents, consequents));
    }
}

// Knowledge Base definition
KnowledgeBase<TSKRule> kb = new KnowledgeBase<>(db, rb);

// Inference
double[] input = new double[]{2, 0};
TSKInference.inference(this.kb, input);
```

## License

The frog library is licensed under the Apache 2 license, quoted below.

    Copyright 2013 Centro de Investigación en Tecnoloxías da Información (CITIUS),
    University of Santiago de Compostela (USC).

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
