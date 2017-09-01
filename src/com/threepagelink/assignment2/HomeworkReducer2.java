package com.threepagelink.assignment2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;


public class HomeworkReducer2 extends Reducer<Text, Text, Text, Text> {
    // The Karmasphere Studio Workflow Log displays logging from Apache Commons Logging, for example:
    // private static final Log LOG = LogFactory.getLog("com.threepagelink.assignment2.HomeworkReducer2");


    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        StringBuilder values_ = new StringBuilder();
        for (Text value : values)
            values_.append(value + ",");
        values_.replace(values_.length() - 1, values_.length(), "");

        context.write(key, new Text(values_.toString()));
    }
}
