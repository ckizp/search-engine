import subprocess

jar_file_path = "engine.jar"
argument1 = "orange"

# Run the JAR file with arguments
process = subprocess.Popen(["java", "-jar", jar_file_path, argument1], stdout=subprocess.PIPE, stderr=subprocess.PIPE)

# Wait for the process to finish and get the output
output, error = process.communicate()
if error:
    print("Error occurred: {}".format(error.decode()))
else:
    print("Output: {}".format(output.decode(encoding="ISO-8859-1")))
