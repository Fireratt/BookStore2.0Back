-- 获取当前目录  
local current_dir = io.popen("pwd"):read("*l")  

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
local index = 1 
-- 等待所有进程完成,并输出结果  