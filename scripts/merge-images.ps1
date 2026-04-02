param(
    [Parameter(Mandatory = $true)]
    [string]$FirstImage,
    [Parameter(Mandatory = $true)]
    [string]$SecondImage,
    [Parameter(Mandatory = $true)]
    [string]$OutputImage
)

Add-Type -AssemblyName System.Drawing

if (-not (Test-Path $FirstImage)) {
    Write-Error "First image not found: $FirstImage"
    exit 1
}

if (-not (Test-Path $SecondImage)) {
    Write-Error "Second image not found: $SecondImage"
    exit 1
}

$img1 = [System.Drawing.Image]::FromFile($FirstImage)
$img2 = [System.Drawing.Image]::FromFile($SecondImage)

$width = [Math]::Max($img1.Width, $img2.Width)
$height = $img1.Height + $img2.Height

$bitmap = New-Object System.Drawing.Bitmap($width, $height)
$graphics = [System.Drawing.Graphics]::FromImage($bitmap)
$graphics.Clear([System.Drawing.Color]::White)

$graphics.DrawImage($img1, 0, 0, $img1.Width, $img1.Height)
$graphics.DrawImage($img2, 0, $img1.Height, $img2.Width, $img2.Height)

$bitmap.Save($OutputImage, [System.Drawing.Imaging.ImageFormat]::Png)

$graphics.Dispose()
$img1.Dispose()
$img2.Dispose()
$bitmap.Dispose()

Write-Output "Merged image saved to: $OutputImage"

