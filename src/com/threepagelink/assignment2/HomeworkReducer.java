package com.threepagelink.assignment2;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.bloom.Key;


public class HomeworkReducer extends Reducer<Text, Text, Text, Text> {
    // The Karmasphere Studio Workflow Log displays logging from Apache Commons Logging, for example:
    // private static final Log LOG = LogFactory.getLog("com.threepagelink.assignment2.HomeworkReducer");

    private Map<String, List<String>> pageLink = new HashMap<>();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        List<String> pages = new ArrayList<>();
        for (Text value : values) {
            pages.add(value.toString());
        }

        pageLink.put(key.toString(), pages);

        for (String value : pages) {
            if (pageLink.containsKey(value)) {
                for (String node : pageLink.get(value)) {
                    if (!key.toString().equalsIgnoreCase(node)) {
                        context.write(new Text(node + "," + value), key);
                    }
                }
            }
            Set<String> keys = pageLink.keySet();
            for (String key_ : keys) {
                if (!key_.equalsIgnoreCase(key.toString()))
                    if (pageLink.get(key_).contains(key.toString())) {
                        if (!value.equalsIgnoreCase(key_))
                            context.write(new Text(value + "," + key), new Text(key_));
                    }
            }
        }


    }
}
