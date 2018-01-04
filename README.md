# GoogleMapPolyLineTest
First entry origin and destination, after click button. app will mark start&amp;end position and draw path.

## 執行流程 ##
    onMapReady:檢查權限
    sendRequestTest:取得起訖點 or sendRequest:取得輸入的起訖點
    directionFinder.execute:執行onDirectionFinderStart
    onDirectionFinderStart:檢查是否有舊的線，有的話刪除
    directionFinder.fetchData:取得JSON
    directionFinder.parseJson:解析JSON，分別取得PolyLine跟起訖點
    onDirectionFinderSuccess:畫線
    markPoint:畫起訖點

## Thread ##
    onMapReady                   mainThread         
    sendRequestTest              mainThread
    directionFinder.execute      mainThread
    onDirectionFinderStart       mainThread
    directionFinder.fetchData    Scheduler.io(RxCachedThreadScheduler-1)
    directionFinder.parseJson    Scheduler.io(RxCachedThreadScheduler-1)
    onResponse/onFailure         Executors.newSingleThreadExecutor()(pool-3-thread-1)
    onDirectionFinderSuccess     mainThread
    markPoint                    mainThread
