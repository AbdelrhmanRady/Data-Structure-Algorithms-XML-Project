# Data-Structure-Algorithms-XML-Project

Language : JAVA

Brief explanation:
XML (Extensible Markup Language) is one of the most famous formats for storing and sharing
information among different devices. Some text editors such as Sublime Text are able to parse
such files and do some basic operations. In this project, you will work on developing a GUI
(Graphical User Interface) based program to parse and visualize an XML file.
![2023-02-02](https://user-images.githubusercontent.com/92223732/216429688-f8add2df-0351-42fd-bca6-8f38ccb0738e.png)


In this project, you will learn how to understand and parse XML and json files. Additionally, you
will work on designing a GUI (Graphical User Interface) to visualize XML and json files. You
should also work on designing the program such that it makes use of optimal data structures
for the implemented features.

Sample of an XML file file:
https://drive.google.com/file/d/1_GeHIjYQZEAZNu6ZxdXEotPnHhiKnaWn/view?usp=sharing

project is distributed into 2 phases
1) 1st Phase:


● Building a GUI in which the user can specify the location of an input XML file:
  using java GUI we interact with user to select an input XML file by browsing pc and then we can do the basic operations as follows. 

![2023-02-02 (1)](https://user-images.githubusercontent.com/92223732/216430068-e5e41cba-d268-4bf6-a843-3bdea3382a74.png)


● Checking the XML consistency ,validating by detecting and correcting: 
The input XML may have inconsistencies like missing any
of the closing and opening tags or not matching tags. 
this includes : -bracket errors
If a tag has an open bracket but no closing bracket.
• If a tag has a closed bracket without an open brackect.
• If both brackets on a tag are open brackets.
• If both brackets on a tag are close brackets.

-tag errors
 If there is an open tag missing a closing tag.
• If there is a closed tag missing an open tag.
• If there are two open tags of the same type consecutively.
• If there are two close tags of the same type consecutively.


●Inserting XML to tree:
 our first step to deal with the file after reading it is by parsing and inserting it to tree data structure to facilitate converting it to JSON, prettifying adn deal with xml data without messy tags and brackets. by relating each data to its user or its class

● Formatting (Prettifying) the XML: the XML file should be well formatted by keeping the
indentation for each level. By levels we mean the depth of a node in a tree. a traversal behavioris used to generate a well formatted xml file keeping its data safe.
![2023-02-02 (4)](https://user-images.githubusercontent.com/92223732/216431291-c28e7789-ce47-4a35-aeb2-a6aa3fa7ac21.png)


● Converting XML to JSON: JSON (Javascript Object Notation) is another format that is used
to represent data. It’s helpful to convert the XML into JSON, especially when using
javascript as there’s tons of libraries and tools that use json notation.
we used the implemented tree to format using brackets and behavior of JSON which is used in the 2nd phase for graphing the XML.
we used depth of each node in tree, covering cases by handling closing and opening of:
-handleSameChildrenTypesNode
-SibilingsWithNoChildrenNode
-NochildrenNoSibilingsNode
-ChildrenAndSibilingsNode
-ChildrenNoSibilingsNode
![2023-02-02 (5)](https://user-images.githubusercontent.com/92223732/216431476-4962fb14-38c0-41df-8183-40f9760c8a83.png)



● Minifying the XML file: Since spaces and newlines (\n) are actually characters that can
increase the size of an XML document. This feature should aim at decreasing the size of
an XML file (compressing it) by deleting the whitespaces and indentations.this function aids compression. it mainly uses the built in functions of java like trim() and append() to avoid polluting data 
e.g body of post in the attached input sample.

![2023-02-02 (6)](https://user-images.githubusercontent.com/92223732/216431671-f1f8a3da-8241-410f-ae08-e6aaea5fa5f5.png)


● Compressing the data in the XML/JSON file:
Compression is based on using Huffman compression/encoding. We first minify 
the given file, generate binary codes to each character in the file (the most 
frequently used character has least number of binary digits). It’s demonstrated in 
using the Huffman part.
used algorithm:

1. Calculate the frequency of each character in the string.
2. Sort the characters in increasing order of the frequency. These are stored in 
a priority queue. Characters sorted according to the frequency.
3. Make each unique character as a leaf node.
4. Create an empty node z. Assign the minimum frequency to the left child of 
z and assign the second minimum frequency to the right child of z. Set the 
value of the z as the sum of the above two minimum frequencies. Getting 
the sum of the least numbers
5. Remove these two minimum frequencies from Q and add the sum into the 
list of frequencies (* denote the internal nodes in the figure above).
6. Insert node z into the tree.
7. Repeat steps 3 to 5 for all the characters.

![2023-02-02 (7)](https://user-images.githubusercontent.com/92223732/216432136-7f4fafdd-3732-466d-a6b7-11db335b6d33.png)


● Decompressing:
the compressed data to return to its original format.
Decompression uses the inverse by transferring the less weighted characters to 
binary again then, we can take the code and traverse through the tree to find 
the character.


more about HUFFMAN: 
https://www.youtube.com/watch?v=21_bJLB7gyU

https://www.programiz.com/dsa/huffman-coding

note: in video and internet resources you can find huffman encoding but not necessarily compression so check code to get it.

2) 2nd phase:
In our system the XML file will represent users in a social network
Each user has id (unique), name, list of posts, list of followers.
Each post has text and list of topics.

representing the users data using the graph data structure: the XML file will represent the
users data in a social network (their posts, followers, ...etc).
The user data is his id (unique across the network), name, list of his posts and followers.
So you should represent the relation between the followers using the graph data
structure as it will be very helpful for the network analysis.
If the input file was like this (the dots mean that there are additional tags inside the user
tag) :
Then you should build a graph relation between the user that looks like the graph
beneath.

● Network analysis: by representing the network using the graph data structure, we can
extract some important data:
- who is the most influencer user (has the most followers)
![2023-02-02 (10)](https://user-images.githubusercontent.com/92223732/216432578-32f8f76f-8c07-4ad5-8e2f-c41e12f4bd13.png)


- who is the most active user (connected to lots of users)
![2023-02-02 (8)](https://user-images.githubusercontent.com/92223732/216432922-ef9d4380-f397-451c-b967-d5f556995b4c.png)

- the mutual followers between 2 users
![2023-02-03](https://user-images.githubusercontent.com/92223732/216471377-3b621d0a-b2c3-4667-bed9-0600dad89c8b.png)


- for each user, suggest a list of users to follow (the followers of his followers)
![2023-02-02 (9)](https://user-images.githubusercontent.com/92223732/216432837-feff9648-e6ee-4982-80ed-d88549bb834f.png)


● Post search: given a specific word or topic, get the posts where this word or topic was
mentioned by iterating line by line over the prettified file. so we either search by word in a post or by topic, in both cases we start by looking for a <body>opneing tag then we search in it ofr the passed word (neglecting its case) if we find it in line, we return all body to user and repeat till end of file,
same idea for topic search but difference is that same post can have many topics

![2023-02-02 (12)](https://user-images.githubusercontent.com/92223732/216433637-ab600122-ec16-4aea-b88a-1c41570cc3de.png)
