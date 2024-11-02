-- 获取当前目录  
local current_dir = io.popen("pwd"):read("*l")  
os.execute("net start mysql84")
-- 遍历当前目录下的所有目录  
local dirs = io.popen("ls -d */"):lines()  
local processes = {}  
for dir in dirs do  
    -- 去掉末尾的斜杠  
    dir = string.sub(dir, 1, -2)  

    -- 进入目录并执行 Maven 命令  
    local output = io.popen("cd " .. dir .. " && call start /min \"n\" mvn spring-boot:run")  
    processes[#processes + 1] = output 
end 
-- goto the parent dir and execute maven to start Main processes
local output = io.popen("cd .." .. " && call start /min \"n\" mvn spring-boot:run")  
processes[#processes + 1] = output 

local index = 1 
-- 等待所有进程完成,并输出结果  