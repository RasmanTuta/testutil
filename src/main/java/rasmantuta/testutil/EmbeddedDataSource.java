package rasmantuta.testutil;

//   Copyright 2011 Kristian Berg (RasmanTuta)
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class EmbeddedDataSource extends TestWatchman {
    private String schema;
    private String dataSet;
    private EmbeddedDatabase dataSource = null;
    

    public EmbeddedDataSource(String schema, String dataSet){
        this.schema = schema;
        this.dataSet = dataSet;
    }

    @Override
    public void starting(FrameworkMethod method) {
        // Get annotated Schema or DataSet if provided.
        DataSet providedDataSet = method.getAnnotation(DataSet.class);
        Schema providedSchema = method.getAnnotation(Schema.class);
        // Decide what Schema or DataSet to use
        dataSet = null == providedDataSet ? dataSet : providedDataSet.value();
        schema = null == providedSchema ? schema : providedSchema.value();

        dataSource = new EmbeddedDatabaseBuilder().addScript(schema).addScript(dataSet).build();
    }

    @Override
    public void finished(FrameworkMethod method) {
        dataSource.shutdown();
    }

    public String getSchema() {
        return schema;
    }

    public String getDataSet() {
        return dataSet;
    }

    public EmbeddedDatabase getDataSource() {
        return dataSource;
    }
}
