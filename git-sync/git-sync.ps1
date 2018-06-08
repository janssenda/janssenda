$ErrorActionPreference = "Stop"

$root="G:\Repositories"

$repositoryList = @{
    "adobe_ust" = "$root\Adobe\adobe_ust";
    "Adobe Notes" = "$root\Adobe\Adobe-Project";
    "install-scripts" = "$root\Adobe\install-scripts";
    "install-scripts-dev" = "$root\Adobe\install-scripts-dev";
    "user-sync.py" = "$root\Adobe\user-sync.py";
    "jenkinsbuild" = "$root\Adobe\jenkinsbuild";
    "flux" = "$root\flux";
	"python-cards" = "$root\python-cards"
	"ust-unofficial" = "$root\Adobe\ust-unofficial"
	"umapi-test" = "$root\Adobe\umapi-test"	
}

Write-Host "`n`n"
Write-Host "Pulling repositories"
Write-Host "----------------------------"

$homedir = ${PWD}
foreach($r in $repositoryList.GetEnumerator()){
    Write-Host $r.Name
    Set-Location -Path $r.Value
    Start-Process -FilePath "git" -ArgumentList "pull" -Wait -NoNewWindow
}

Write-Host "----------------------------"

Set-Location -Path $homedir