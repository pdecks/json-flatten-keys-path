# json-flatten-keys-path

Simple program that takes a JSON object as input and outputs (to file) a flattened version of the JSON object, with keys as the path to every terminal value in the JSON structure.

_Built with Maven._

#### To run from command line

- Build the project

    `mvn compile`
    
- Run `KeysPathJsonFlattener.main()` using mvn and piping input, in this example from a file

    ` cat ./jsonSamples.json | mvn exec:java -Dexec.mainClass="KeysPathJsonFlattener"`
    
- The generated output file will be at the top of the project directory, i.e.:

    `<path-to-project>/json-flatten-keys-path/output.json`
    
    
#### Sample Test Case

##### Input

```$xslt
{
    "a": 1,
    "b": true,
    "c": {
        "d": 3,
        "e": "test"
    }
}
```

##### Output

```$xslt
{
  "a": 1,
  "b": true,
  "c.d": 3,
  "c.e": "test"
}
```


 
