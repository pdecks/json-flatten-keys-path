# json-flatten-keys-path

Simple program that takes a JSON object as input and outputs (to file) a flattened version of the JSON object, with keys as the path to every terminal value in the JSON structure.

_Built with Maven._

### Run From Command Line Using Maven

#### Build the project

`mvn compile`

You should see the following output:

```dtd
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.474 s
[INFO] Finished at: 2020-09-06T10:07:55-07:00
[INFO] ------------------------------------------------------------------------
```
    
#### Invoke `mvn exec:java`

* To pipe input from `stdin`:

    `cat ./jsonSamples.json | mvn exec:java -Dexec.mainClass="KeysPathJsonFlattener"`
    
- To specify input from a file directly, pass the space-separated arguments `-file <absolute_file_path>` using Maven's `-Dexec.args`:

    `mvn exec:java -Dexec.mainClass="KeysPathJsonFlattener" -Dexec.args="'-file' '/Users/patriciadecker/IdeaProjects/json-flatten-keys-path/jsonSamples.json'"`
    
For either input source, you should see the following output, where the generated output file will be at the top of the project directory, i.e. `<path-to-project>/json-flatten-keys-path/output.json`:

```dtd
File written successfully to /Users/patriciadecker/IdeaProjects/json-flatten-keys-path/output.json
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.380 s
[INFO] Finished at: 2020-09-06T10:09:21-07:00
[INFO] ------------------------------------------------------------------------
``` 

### Test Cases
    
#### Unit Tests
The unit tests consume input and expected output files within `/src/test/resources`

` mvn test`    

You should see the following output

```dtd
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running KeysPathJsonFlattenerTest
Tests run: 4, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.059 sec

Results :

Tests run: 4, Failures: 0, Errors: 0, Skipped: 1
```

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


 
